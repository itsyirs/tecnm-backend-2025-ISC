package mx.tecnm.backend.api.repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import mx.tecnm.backend.api.models.Categoria;
public class CategoriaRM implements RowMapper<Categoria> {
    @Override
    public Categoria mapRow(@NonNull java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Categoria(
                rs.getInt("id"),
                rs.getString("nombre"));
    }

}
