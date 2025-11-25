package mx.tecnm.backend.api.repository;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.lang.NonNull;

import mx.tecnm.backend.api.models.Sexo;
import mx.tecnm.backend.api.models.Usuario;

public class UsuarioRM implements RowMapper<Usuario> {
    @Override
     public Usuario mapRow(@NonNull java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("telefono").toCharArray(),
                Sexo.valueOf(rs.getString("sexo")),
                rs.getString("fecha_nacimiento"),
                rs.getString("contrasena"),
                rs.getString("fecha_registro"));
    }

}
