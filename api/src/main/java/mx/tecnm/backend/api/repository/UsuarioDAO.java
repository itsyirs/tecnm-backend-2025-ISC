package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Usuario;

@Repository
public class UsuarioDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<Usuario> obtenerUsuarios() {
        String sql = "SELECT id, nombre, email, telefono, sexo, fecha_nacimiento, contrasena, fecha_registro FROM usuarios";
        return jdbcClient.sql(sql)
                .query(new UsuarioRM())
                .list();
    }
}
