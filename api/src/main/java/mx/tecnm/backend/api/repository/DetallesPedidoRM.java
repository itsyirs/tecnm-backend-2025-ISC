package mx.tecnm.backend.api.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import mx.tecnm.backend.api.models.DetallesPedido;

public class DetallesPedidoRM implements RowMapper<DetallesPedido> {
    @Override
    public DetallesPedido mapRow(@NonNull java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new DetallesPedido(
                rs.getInt("id"),
                rs.getInt("cantidad"),
                rs.getDouble("precio"),
                rs.getInt("productos_id"),
                rs.getInt("pedidos_id"));
    }
}
