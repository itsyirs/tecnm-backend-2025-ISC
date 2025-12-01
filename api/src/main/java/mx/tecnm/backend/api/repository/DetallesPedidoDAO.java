package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.DetallesPedido;
@Repository
public class DetallesPedidoDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<DetallesPedido> obtenerDetallesPedidos() {
        String sql = "SELECT id, cantidad, precio, productos_id, pedidos_id FROM detalles_pedido WHERE id=true)";
        return jdbcClient.sql(sql)
                .query(new DetallesPedidoRM())
                .list();
    }
    public DetallesPedido obtenerPorId(int id) {
        String sql = """
            SELECT id, pedidos_id, productos_id, cantidad, precio_unitario, activo
            FROM detalles_pedido
            WHERE id = :id and activo=true""";

        List<DetallesPedido> lista = jdbcClient.sql(sql)
                .param("id", id)
                .query(new DetallesPedidoRM())
                .list();

        return lista.isEmpty() ? null : lista.get(0);
    }
    //crear DetallesPedido
    public DetallesPedido insertar(DetallesPedidosDTO dto) {
        int nuevoId = jdbcClient.sql("""
                INSERT INTO detalles_pedido(pedidos_id, productos_id, cantidad, precio_unitario)
                VALUES (:pedidos_id, :productos_id, :cantidad, :precio)
                RETURNING id
            """)
            .param("pedidos_id", dto.pedidosId())
            .param("productos_id", dto.productosId())
            .param("cantidad", dto.cantidad())
            .param("precio_unitario", dto.precioUnitario())
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return obtenerPorId(nuevoId);
    }

    public DetallesPedido actualizar(int id, DetallesPedidoDTO dto) {
        int filas = jdbcClient.sql("""
                UPDATE detalles_pedido
                SET pedidos_id = :pedidos_id,
                    productos_id = :productos_id,
                    cantidad = :cantidad,
                    precio = :precio
                WHERE id = :id
                RETURNING id
            """)
            .param("id", id)
            .param("pedidos_id", dto.pedidosId())
            .param("productos_id", dto.productosId())
            .param("cantidad", dto.cantidad())
            .param("precio", dto.precio())
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return filas > 0 ? obtenerPorId(id) : null;
    }
    @DeleteMapping("/{id}")
    public DetallesPedido desactivar(int id) {
        int filas = jdbcClient.sql("""
                UPDATE detalles_pedido
                SET activo = false
                WHERE id = :id
                RETURNING id
            """)
            .param("id", id)
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return filas > 0 ? obtenerPorId(id) : null;
    }
}
