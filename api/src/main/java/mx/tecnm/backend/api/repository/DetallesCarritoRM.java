package mx.tecnm.backend.api.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import mx.tecnm.backend.api.models.DetallesCarrito;

public class DetallesCarritoRM implements RowMapper<DetallesCarrito> {
    @Override
    public DetallesCarrito mapRow(@NonNull java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new DetallesCarrito(
            rs.getInt("id"),
            rs.getInt("cantidad"),
            rs.getFloat("precio"),
            rs.getInt("productos_id"),
            rs.getInt("usuarios_id")
        );
    }

}
