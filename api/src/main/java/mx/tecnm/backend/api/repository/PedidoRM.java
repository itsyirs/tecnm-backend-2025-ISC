package mx.tecnm.backend.api.repository;

import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import mx.tecnm.backend.api.models.Pedido;

public class PedidoRM implements RowMapper<Pedido>{
    @Override
    public Pedido mapRow(@NonNull java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Pedido(
                rs.getInt("id"),
                rs.getString("fecha"),
                rs.getDouble("importe_productos"),
                rs.getDouble("importe_envio"),
                rs.getInt("usuarios_id"),
                rs.getInt("metodos_pago_id"),
                rs.getString("fecha_hora_pago"),
                rs.getDouble("importe_iva"),
                rs.getDouble("total"),
                UUID.fromString(rs.getString("numero"))
        );
    }
}
