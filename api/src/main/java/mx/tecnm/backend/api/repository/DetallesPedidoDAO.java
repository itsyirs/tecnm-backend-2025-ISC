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
        String sql = "SELECT id, cantidad, precio, productos_id, pedidos_id FROM detalles_pedido";
        return jdbcClient.sql(sql)
                .query(new DetallesPedidoRM())
                .list();
    }

}
