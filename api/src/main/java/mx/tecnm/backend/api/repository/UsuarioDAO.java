package mx.tecnm.backend.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Usuario;
import mx.tecnm.dto.UsuarioDTO;

@Repository
public class UsuarioDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<Usuario> obtenerUsuarios() {
        String sql = "SELECT id, nombre, email, telefono, sexo, fecha_nacimiento, contrasena, fecha_registro FROM usuarios WHERE activo=true";
        return jdbcClient.sql(sql)
                .query(new UsuarioRM())
                .list();
    }
    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT id, nombre, email, telefono, sexo, fecha_nacimiento, contrasena, fecha_registro FROM usuarios WHERE id = :id";
        List<Usuario> usuarios = jdbcClient.sql(sql)
                .param("id", id)
                .query(new UsuarioRM())
                .list();
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }
    public Usuario insertarUsuario(UsuarioDTO usuario) {
        int filas = jdbcClient.sql("INSERT INTO usuarios(nombre, email, telefono, sexo, fecha_nacimiento, contrasena) VALUES (:nombre,:email,:telefono,:sexo::sexo_enum,:fecha_nacimiento,:contrasena) RETURNING id")
            .param("nombre",usuario.nombre())
            .param("email",usuario.email())
            .param("telefono",usuario.telefono())
            .param("sexo",usuario.sexo().name()) // pass enum name directly; SQL casts with ?::sexo_enum
            .param("fecha_nacimiento",usuario.fecha_nacimiento())
            .param("contrasena",usuario.contrasena())
            .query((rs,rowNum) -> rs.getInt("id"))
            .single();
        return obtenerUsuarioPorId(filas);
    }
    public Usuario desactivarUsuario(int id) {
        int filas = jdbcClient.sql("UPDATE usuarios SET activo=false WHERE id=:id RETURNING id")
            .param("id", id)
            .query((rs,rowNum) -> rs.getInt("id"))
            .single();
        return filas == 1 ? obtenerUsuarioPorId(filas) : null;
    }
}
