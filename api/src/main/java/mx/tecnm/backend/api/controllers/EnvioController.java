package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import mx.tecnm.backend.api.dto.EnvioDTO;
import mx.tecnm.backend.api.models.Envio;
import mx.tecnm.backend.api.repository.EnvioDAO;

@RestController
@RequestMapping("/envios")
public class EnvioController {
    @Autowired
    private EnvioDAO envioDAO;
    @GetMapping()
    public ResponseEntity<List<Envio>> obtenerEnvios() {
        List<Envio> resultado = envioDAO.obtenerEnvios();
        return ResponseEntity.ok(resultado);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerEnvioPorId(@PathVariable int id) {
        Envio envio = envioDAO.obtenerEnvioPorId(id);
        if (envio != null) {
            return ResponseEntity.ok(envio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> insertarEnvio(@RequestBody EnvioDTO envio) {
        System.out.println("DTO recibido: " + envio);
        Envio envioInsertado = envioDAO.insertarEnvio(envio);
        if (envioInsertado != null) {
            return ResponseEntity.status(201).body(envioInsertado);
        } else {
            return ResponseEntity.status(500).body("Error al agregar el envio");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEnvio(@PathVariable int id, @RequestBody Envio envio) {
        Envio envioActualizado = envioDAO.actualizarEnvio(id, envio);
        if (envioActualizado != null) {
            return ResponseEntity.ok(envioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarEnvio(@PathVariable int id) {
        Envio envioDesactivado = envioDAO.desactivarEnvio(id);
        if (envioDesactivado != null) {
            return ResponseEntity.ok(envioDesactivado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
