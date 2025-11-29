package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.DetallesCarrito;
import mx.tecnm.backend.api.models.Pedido;

@Repository
public class DetallesCarritoDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<DetallesCarrito> obtenerDetallesCarrito() {
        String sql = "SELECT id, cantidad, precio, productos_id, usuarios_id FROM detalles_carrito";
        return jdbcClient.sql(sql)
                .query(new DetallesCarritoRM())
                .list();
    }

    public DetallesCarrito agregarProductoAlCarrito(int usuarioId, int productoId, int cantidadAgregar, double precioUnitario) {
    // comprobar si el producto ya existe en el carrito del usuario
    String sqlBuscar = "SELECT id, cantidad, precio, productos_id, usuarios_id " +
                       "FROM detalles_carrito " +
                       "WHERE usuarios_id = :usuarioId AND productos_id = :productoId";

    List<DetallesCarrito> existentes = jdbcClient.sql(sqlBuscar)
            .param("usuarioId", usuarioId)
            .param("productoId", productoId)
            .query(new DetallesCarritoRM())
            .list();

    // si existe sumamos la cantidad
    if (!existentes.isEmpty()) {
        DetallesCarrito existente = existentes.get(0);
        int nuevaCantidad = existente.cantidad() + cantidadAgregar;
        double nuevoPrecio = precioUnitario * nuevaCantidad; // o conserva el precio como lo manejes

        String sqlActualizar = "UPDATE detalles_carrito " +
                               "SET cantidad = :cantidad, precio = :precio " +
                               "WHERE id = :id " +
                               "RETURNING id, cantidad, precio, productos_id, usuarios_id";

        return jdbcClient.sql(sqlActualizar)
                .param("cantidad", nuevaCantidad)
                .param("precio", nuevoPrecio)
                .param("id", existente.id())
                .query(new DetallesCarritoRM())
                .single();
    }

    // agregar nuevo producto al carrito
    String sqlInsertar = "INSERT INTO detalles_carrito (cantidad, precio, productos_id, usuarios_id) " +
                         "VALUES (:cantidad, :precio, :productoId, :usuarioId) " +
                         "RETURNING id, cantidad, precio, productos_id, usuarios_id";

    return jdbcClient.sql(sqlInsertar)
            .param("cantidad", cantidadAgregar)
            .param("precio", precioUnitario * cantidadAgregar)
            .param("productoId", productoId)
            .param("usuarioId", usuarioId)
            .query(new DetallesCarritoRM())
            .single();
}
//quitar un producto
public boolean quitarProductoDelCarrito(int detalleId) {
    String sql = "DELETE FROM detalles_carrito WHERE id = :id";
    int rows = jdbcClient.sql(sql)
            .param("id", detalleId)
            .update();
    return rows > 0;
}

//crear pedido a partir del carrito
public Pedido generarPedido(int usuarioId) {

    // 1. Obtener los productos del carrito del usuario
    String sqlCarrito = "SELECT id, cantidad, precio, productos_id, usuarios_id "
            + "FROM detalles_carrito WHERE usuarios_id = :usuarioId";

    List<DetallesCarrito> carrito = jdbcClient.sql(sqlCarrito)
            .param("usuarioId", usuarioId)
            .query(new DetallesCarritoRM())
            .list();

    if (carrito.isEmpty()) {
        return null; // No hay productos
    }

    // 2. Crear el pedido
    String sqlPedido = """
            INSERT INTO pedido (usuarios_id, fecha, total)
            VALUES (:usuarioId, NOW(), :total)
            RETURNING id, usuarios_id, fecha, total
            """;

    // Calcular total
    double total = carrito.stream()
            .mapToDouble(item -> item.precio() * item.cantidad())
            .sum();

    Pedido pedido = jdbcClient.sql(sqlPedido)
            .param("usuarioId", usuarioId)
            .param("total", total)
            .query(new PedidoRM())
            .single();

    // 3. Insertar los detalles del pedido
    String sqlDetalles = """
            INSERT INTO detalles_pedido (pedido_id, productos_id, cantidad, precio)
            VALUES (:pedidoId, :productoId, :cantidad, :precio)
            """;

    for (DetallesCarrito item : carrito) {
        jdbcClient.sql(sqlDetalles)
                .param("pedidoId", pedido.id())
                .param("productoId", item.productos_id())
                .param("cantidad", item.cantidad())
                .param("precio", item.precio())
                .update();
    }

    // 4. Vaciar carrito
    String sqlVaciar = "DELETE FROM detalles_carrito WHERE usuarios_id = :usuarioId";

    jdbcClient.sql(sqlVaciar)
            .param("usuarioId", usuarioId)
            .update();

    return pedido;
}
}
