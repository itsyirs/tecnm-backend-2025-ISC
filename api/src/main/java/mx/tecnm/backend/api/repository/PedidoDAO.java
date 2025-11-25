package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Pedido;

@Repository
public class PedidoDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<Pedido> obtenerPedidos() {
        String sql = "SELECT id, fecha, importe_productos, importe_envio, usuarios_id, metodos_pago_id, fecha_hora_pago, importe_iva, total, numero FROM pedidos";
        return jdbcClient.sql(sql)
                .query(new PedidoRM())
                .list();
    }
}
