package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.EstadoEnvio;
import mx.tecnm.backend.api.dto.EnvioDTO;
import mx.tecnm.backend.api.models.Envio;

@Repository
public class EnvioDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<Envio> obtenerEnvios() {
        String sql = "SELECT id, fecha_entrega, fecha_envio, estado, numero_seguimiento, domicilios_id, pedidos_id FROM envios WHERE activo=true";
        return jdbcClient.sql(sql)
                .query(new EnvioRM())
                .list();
    }
    public Envio obtenerEnvioPorId(int id) {
        String sql = "SELECT id, fecha_entrega, fecha_envio, estado, numero_seguimiento, domicilios_id, pedidos_id FROM envios WHERE id = ?";
        return jdbcClient.sql(sql)
                .param(id)
                .query(new EnvioRM())
                .optional()
                .orElse(null);
    }
    public Envio insertarEnvio(EnvioDTO envio) {
         String numeroSeguimiento = java.util.UUID.randomUUID().toString();
        String sql = "INSERT INTO envios (fecha_entrega, fecha_envio, estado, numero_seguimiento, domicilios_id, pedidos_id) VALUES (:fecha_entrega, :fecha_envio, :estado::estado_envio_enum, :numero_seguimiento, :domicilios_id, :pedidos_id) RETURNING id";
        int nuevoId = jdbcClient.sql(sql)
            .param("fecha_entrega", envio.fecha_entrega())
            .param("fecha_envio", envio.fecha_envio())
            .param("estado", envio.estado() != null ? envio.estado().name() : EstadoEnvio.PENDIENTE.name())
            .param("numero_seguimiento", numeroSeguimiento)
            .param("domicilios_id", envio.domicilios_id())
            .param("pedidos_id", envio.pedidos_id())
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();
        return obtenerEnvioPorId(nuevoId);
    }
   

    public Envio actualizarEnvio(int id, Envio envio) {
        int filas = jdbcClient.sql("UPDATE envios SET fecha_entrega=:fecha_entrega, fecha_envio=:fecha_envio, estado=:estado::estado_envio_enum, numero_seguimiento=:numero_seguimiento, domicilios_id=:domicilios_id, pedidos_id=:pedidos_id WHERE id=:id RETURNING id")
            .param("id", id)
            .param("fecha_entrega", envio.fecha_entrega())
            .param("fecha_envio", envio.fecha_envio())
            .param("estado", envio.estado().name())
            .param("numero_seguimiento", envio.numero_seguimiento())
            .param("domicilios_id", envio.domicilios_id())
            .param("pedidos_id", envio.pedidos_id())
            .query((rs,rowNum) -> rs.getInt("id"))
            .single();
        return filas > 0 ? obtenerEnvioPorId(id) : null;
    }
    public Envio desactivarEnvio(int id) {
        int filas = jdbcClient.sql("UPDATE envios SET activo=false WHERE id=:id RETURNING id")
            .param("id", id)
            .query((rs,rowNum) -> rs.getInt("id"))
            .single();
        return filas > 0 ? obtenerEnvioPorId(id) : null;
    }
}
