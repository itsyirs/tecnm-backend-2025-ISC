package mx.tecnm.backend.api.repository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;


import mx.tecnm.backend.api.models.Categoria;
@Repository
public class CategoriaDAO {
    @Autowired
    private JdbcClient jdbcClient;

    public List<Categoria> obtenerCategorias() {
        String sql = "SELECT id, nombre FROM categorias WHERE activo=true";
        return jdbcClient.sql(sql)
                .query(new CategoriaRM())
                .list();
    }
    public Categoria obtenCategoriaPorId(int id){
        String sql= "SELECT id, nombre FROM categorias WHERE id = :id";
        List<Categoria> categorias = jdbcClient.sql(sql)
            .param("id", id)
            .query(new CategoriaRM())
            .list();
        
        return categorias.isEmpty() ? null : categorias.get(0);
    }
    public Categoria insertarCategoria(String categoria) {
        String sql = "INSERT INTO categorias (nombre) VALUES (:nombre) RETURNING id, nombre";
        return jdbcClient.sql(sql)
                .param("nombre", categoria)
                .query(new CategoriaRM())
                .single();
    }
    public Categoria actualizarCategoria(int id, String nombre) {
        String sql = "UPDATE categorias SET nombre = :nombre WHERE id = :id RETURNING id, nombre";
        return jdbcClient.sql(sql)
                .param("id", id)
                .param("nombre", nombre)
                .query(new CategoriaRM())
                .single();
    }
    public Categoria desactivarCategoria(int id) {
        String sql = "UPDATE categorias SET activo = false WHERE id = :id RETURNING id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(new CategoriaRM())
                .single();
    }
    
}
