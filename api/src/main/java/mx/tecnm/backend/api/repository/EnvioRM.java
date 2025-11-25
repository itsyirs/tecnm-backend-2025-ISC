package mx.tecnm.backend.api.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import mx.tecnm.backend.api.models.Envio;
import mx.tecnm.backend.api.models.EstadoEnvio;

public class EnvioRM implements RowMapper<Envio>{
    @Override
    public Envio mapRow(@NonNull java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Envio(
                rs.getInt("id"),
                rs.getString("fecha_entrega"),
                rs.getString("fecha_envio"),
                EstadoEnvio.valueOf(rs.getString("estado")),
                rs.getString("numero_seguimiento"),
                rs.getInt("domicilios_id"),
                rs.getInt("pedidos_id"));
    }
}
