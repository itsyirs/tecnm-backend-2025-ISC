package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class MetodoPagoDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<mx.tecnm.backend.api.models.MetodoPago> obtenerMetodosPago() {
        String sql = "SELECT id, nombre, comision FROM metodos_pago";
        return jdbcClient.sql(sql)
                .query(new MetodoPagoRW())
                .list();
    }
}
