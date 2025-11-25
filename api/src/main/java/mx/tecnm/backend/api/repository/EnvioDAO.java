package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Envio;

@Repository
public class EnvioDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<Envio> obtenerEnvios() {
        String sql = "SELECT id, fecha_entrega, fecha_envio, estado, numero_seguimiento, domicilios_id, pedidos_id FROM envios";
        return jdbcClient.sql(sql)
                .query(new EnvioRM())
                .list();
    }
}
