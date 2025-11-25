package mx.tecnm.backend.api.repository;

import org.springframework.jdbc.core.RowMapper;

import io.micrometer.common.lang.NonNull;
import mx.tecnm.backend.api.models.Producto;

public class ProductoRM implements RowMapper<Producto>{
    @Override
    public Producto mapRow(@NonNull java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
                return new Producto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getFloat("precio"),
                rs.getString("sku"),
                rs.getString("color"),
                rs.getString("marca"),
                rs.getString("descripcion"),
                rs.getFloat("peso"),
                rs.getFloat("alto"),
                rs.getFloat("ancho"),
                rs.getFloat("profundidad"),
                rs.getInt("categorias_id"));
    }

}
