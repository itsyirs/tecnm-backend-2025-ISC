package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;


import mx.tecnm.backend.api.models.DetallesCarrito;
import mx.tecnm.backend.api.models.Pedido;
import mx.tecnm.backend.api.models.Producto;
import mx.tecnm.backend.api.dto.AgregarPedidoDTO;
import mx.tecnm.backend.api.dto.PUTDetallesCarritoDTO;
import mx.tecnm.backend.api.dto.DetallesCarritoDTO;
import mx.tecnm.backend.api.dto.DetallesPedidoDTO;

@Repository
public class DetallesCarritoDAO {
    @Autowired
    private JdbcClient jdbcClient;
    @Autowired
    private ProductoDAO productoDAO;
    public List<DetallesCarrito> obtenerDetallesCarrito() {
        String sql = "SELECT id, cantidad, precio, productos_id, usuarios_id FROM detalles_carrito";
        return jdbcClient.sql(sql)
                .query(new DetallesCarritoRM())
                .list();
    }
    public DetallesCarrito obtenerDetallesCarritoPorId(int id) {
        String sql = """
            SELECT id, cantidad, precio, productos_id, usuarios_id
            FROM detalles_carrito
            WHERE id = :id""";

        List<DetallesCarrito> lista = jdbcClient.sql(sql)
                .param("id", id)
                .query(new DetallesCarritoRM())
                .list();

        return lista.isEmpty() ? null : lista.get(0);
    }
    public DetallesCarrito insertarDetallesCarrito(DetallesCarritoDTO dto) {
        Producto producto = productoDAO.obtenerProductoPorId(dto.productos_id());
        List<DetallesCarrito> existente = obtenerDetallesCarrito();
        for (int i=0;i<existente.size();i++){
            if(existente.get(i).productos_id()==dto.productos_id() && existente.get(i).usuarios_id()==dto.usuarios_id()){
                int nuevaCantidad= existente.get(i).cantidad()+dto.cantidad();
                double nuevoPrecio= producto != null ? producto.precio() * nuevaCantidad : 0.0;

                String sqlActualizar = """
                        UPDATE detalles_carrito
                        SET cantidad = :cantidad,
                            precio = :precio
                        WHERE id = :id
                        RETURNING id, cantidad, precio, productos_id, usuarios_id
                        """;

                return jdbcClient.sql(sqlActualizar)
                        .param("cantidad", nuevaCantidad)
                        .param("precio", nuevoPrecio)
                        .param("id", existente.get(i).id())
                        .query(new DetallesCarritoRM())
                        .single();
            }
        }
        double precio= producto != null ? producto.precio() : 0.0;
        String sql = """
                INSERT INTO detalles_carrito (cantidad, precio, productos_id, usuarios_id)
                VALUES (:cantidad, :precio, :productos_id, :usuarios_id)
                RETURNING id, cantidad, precio, productos_id, usuarios_id
                """;

        return jdbcClient.sql(sql)
                .param("cantidad", dto.cantidad())
                .param("precio", precio)
                .param("productos_id", dto.productos_id())
                .param("usuarios_id", dto.usuarios_id())
                .query(new DetallesCarritoRM())
                .single();
    }
    public DetallesCarrito actualizarCantidad(PUTDetallesCarritoDTO dto) {
        double nuevoPrecio = productoDAO.obtenerProductoPorId(dto.productos_id()).precio() * dto.cantidad();
        DetallesCarrito filas = jdbcClient.sql("""
                UPDATE detalles_carrito
                SET cantidad = :cantidad,
                    precio = :precio
                WHERE productos_id = :id
                RETURNING id, cantidad, precio, productos_id, usuarios_id
            """)
            .param("id", dto.productos_id())
            .param("cantidad", dto.cantidad())
            .param("precio", nuevoPrecio)
            .query(new DetallesCarritoRM())
            .single();

        return filas;
    }
     
    
   
//quitar un producto
public boolean quitarProductoDelCarrito(PUTDetallesCarritoDTO dto) {
    List<DetallesCarrito> existente = obtenerDetallesCarrito();
    for (int i=0;i<existente.size();i++){
        if(existente.get(i).productos_id()==dto.productos_id() && existente.get(i).usuarios_id()==dto.usuarios_id()){
            int nuevaCantidad= existente.get(i).cantidad()-dto.cantidad();
            if(nuevaCantidad>0){
                Producto producto = productoDAO.obtenerProductoPorId(dto.productos_id());
                double nuevoPrecio= producto != null ? producto.precio() * nuevaCantidad : 0.0;

               String sqlActualizar = """
        UPDATE detalles_carrito
        SET cantidad = :cantidad,
            precio = :precio
        WHERE id = :id
        """;

        jdbcClient.sql(sqlActualizar)
        .param("cantidad", nuevaCantidad)
        .param("precio", nuevoPrecio)
        .param("id", existente.get(i).id())
        .update();
        return true;
            }
        }
    }
    String sql = "DELETE FROM detalles_carrito WHERE productos_id = :id and usuarios_id = :usuarioId";
    int rows = jdbcClient.sql(sql)
            .param("id", dto.productos_id())
            .param("usuarioId", dto.usuarios_id())
            .update();
    return rows > 0;
}

//crear pedido a partir del carrito
public Pedido generarPedido(AgregarPedidoDTO dto) {

    // 1. Obtener los productos del carrito del usuario
    String sqlCarrito = "SELECT id, cantidad, precio, productos_id, usuarios_id "
            + "FROM detalles_carrito WHERE usuarios_id = :usuarios_id";

    List<DetallesCarrito> carrito = jdbcClient.sql(sqlCarrito)
            .param("usuarios_id", dto.usuarios_id())
            .query(new DetallesCarritoRM())
            .list();

    if (carrito.isEmpty()) {
        return null; // No hay productos
    }

    // 2. Crear el pedido

       
String sqlPedido = """
    INSERT INTO pedidos (usuarios_id, importe_productos, importe_envio, metodos_pago_id)
    VALUES (:usuarios_id, :importe_productos, :importe_envio, :metodos_pago_id)
    RETURNING id, usuarios_id, fecha, total, importe_productos, importe_envio, metodos_pago_id, fecha_hora_pago,
        importe_iva,total, numero
""";

    // Calcular total
    double importe_productos = carrito.stream()
            .mapToDouble(item -> item.precio()*item.cantidad())
            .sum();
    Pedido pedido = jdbcClient.sql(sqlPedido)
            .param("usuarios_id", dto.usuarios_id())
            .param("importe_productos", importe_productos)
            .param("importe_envio", dto.importe_envio())
                .param("metodos_pago_id", dto.metodos_pago_id())
            .query(new PedidoRM())
            .single();

    // 3. Insertar los detalles del pedido
    String sqlDetalles = """
            INSERT INTO detalles_pedido (pedidos_id, productos_id, cantidad, precio)
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
    String sqlVaciar = "DELETE FROM detalles_carrito WHERE usuarios_id = :usuarios_id";

    jdbcClient.sql(sqlVaciar)
            .param("usuarios_id", dto.usuarios_id())
            .update();

    return pedido;
}
}
