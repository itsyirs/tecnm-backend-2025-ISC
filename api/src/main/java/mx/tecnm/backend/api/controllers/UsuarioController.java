package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Usuario;
import mx.tecnm.backend.api.repository.UsuarioDAO;
import mx.tecnm.dto.UsuarioDTO;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController 
{
    @Autowired
    private UsuarioDAO usuarioDAO;
    @GetMapping()
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> resultado = usuarioDAO.obtenerUsuarios();
        return ResponseEntity.ok(resultado);
    } 
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable int id) {
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> insertarUsuario(@RequestBody UsuarioDTO usuario) {
        Usuario usuarioInsertado = usuarioDAO.insertarUsuario(usuario);
        if (usuarioInsertado != null) {
            return ResponseEntity.status(201).body(usuarioInsertado);
        } else {
            return ResponseEntity.status(500).body("Error al agregar el usuario");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarUsuario(@PathVariable int id) {
        Usuario usuarioDesactivado = usuarioDAO.desactivarUsuario(id);
        if (usuarioDesactivado != null) {
            return ResponseEntity.ok(usuarioDesactivado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
