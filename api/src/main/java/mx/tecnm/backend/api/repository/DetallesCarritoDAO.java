package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.DetallesCarrito;

@Repository
public class DetallesCarritoDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<DetallesCarrito> obtenerDetallesCarrito() {
        String sql = "SELECT id, cantidad, precio, productos_id, usuarios_id FROM detalles_carrito";
        return jdbcClient.sql(sql)
                .query(new DetallesCarritoRM())
                .list();
    }
}
