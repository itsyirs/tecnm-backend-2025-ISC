package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.dto.PedidoDTO;
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
    public Pedido obtenerPedidoPorId(int id) {
        String sql = "SELECT id, fecha, importe_productos, importe_envio, usuarios_id, metodos_pago_id, fecha_hora_pago, importe_iva, total, numero FROM pedidos WHERE id = :id";
        List<Pedido> pedidos = jdbcClient.sql(sql)
                .param("id", id)
                .query(new PedidoRM())
                .list();
        return pedidos.isEmpty() ? null : pedidos.get(0);
    }
    public Pedido insertarPedido(PedidoDTO pedido) {
        int filas = jdbcClient.sql("INSERT INTO pedidos(importe_productos, importe_envio, usuarios_id, metodos_pago_id) VALUES (:importe_productos, :importe_envio, :usuarios_id, :metodos_pago_id) RETURNING id")
            .param("importe_productos", pedido.importe_productos())
            .param("importe_envio", pedido.importe_envio())
            .param("usuarios_id", pedido.usuarios_id())
            .param("metodos_pago_id", pedido.metodos_pago_id())
            .query((rs,rowNum) -> rs.getInt("id"))
            .single();
        return obtenerPedidoPorId(filas);
    }
    public Pedido desactivarPedido(int id) {
        int filas = jdbcClient.sql("UPDATE pedidos SET activo=false WHERE id=:id RETURNING id")
            .param("id", id)
            .query((rs,rowNum) -> rs.getInt("id"))
            .single();
        return filas == 1 ? obtenerPedidoPorId(filas) : null;
    }
}