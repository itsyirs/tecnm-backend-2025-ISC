package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Domicilio;

@Repository
public class DomicilioDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<Domicilio> obtenerDomicilios() {
        String sql = "SELECT id, calle, numero, colonia, cp, estado, ciudad, usuarios_id FROM domicilios";
        return jdbcClient.sql(sql)
                .query(new DomicilioRM())
                .list();
    }
}
