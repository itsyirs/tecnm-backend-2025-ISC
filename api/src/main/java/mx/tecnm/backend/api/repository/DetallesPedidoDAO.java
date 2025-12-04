package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.dto.DetallesPedidoDTO;
import mx.tecnm.backend.api.models.DetallesPedido;

@Repository
public class DetallesPedidoDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<DetallesPedido> obtenerDetallesPedidos() {
        String sql = "SELECT id, cantidad, precio, productos_id, pedidos_id FROM detalles_pedido WHERE activo=true";
        return jdbcClient.sql(sql)
                .query(new DetallesPedidoRM())
                .list();
    }
    public DetallesPedido obtenerDetallesPedidoPorId(int id) {
        String sql = """
            SELECT id, pedidos_id, productos_id, cantidad, precio
            FROM detalles_pedido
            WHERE id = :id and activo=true""";

        List<DetallesPedido> lista = jdbcClient.sql(sql)
                .param("id", id)
                .query(new DetallesPedidoRM())
                .list();

        return lista.isEmpty() ? null : lista.get(0);
    }
    //crear DetallesPedido
    public DetallesPedido insertarDetallesPedido(DetallesPedidoDTO dto) {
        int nuevoId = jdbcClient.sql("""
                INSERT INTO detalles_pedido(pedidos_id, productos_id, cantidad, precio)
                VALUES (:pedidos_id, :productos_id, :cantidad, :precio)
                RETURNING id
            """)
            .param("pedidos_id", dto.pedidos_id())
            .param("productos_id", dto.productos_id())
            .param("cantidad", dto.cantidad())
            .param("precio_unitario", dto.precio())
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return obtenerDetallesPedidoPorId(nuevoId);
    }

    public DetallesPedido actualizarDetallesPedido(int id, DetallesPedidoDTO dto) {
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
            .param("pedidos_id", dto.pedidos_id())
            .param("productos_id", dto.productos_id())
            .param("cantidad", dto.cantidad())
            .param("precio", dto.precio())
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return filas > 0 ? obtenerDetallesPedidoPorId(id) : null;
    }
    public DetallesPedido desactivarDetallesPedido(int id) {
        int filas = jdbcClient.sql("""
                UPDATE detalles_pedido
                SET activo = false
                WHERE id = :id
                RETURNING id
            """)
            .param("id", id)
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return filas > 0 ? obtenerDetallesPedidoPorId(id) : null;
    }

}
