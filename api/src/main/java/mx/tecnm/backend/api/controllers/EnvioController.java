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
    @PostMapping()
    public ResponseEntity<?> crearEnvio(@RequestBody EnvioDTO envioDTO) {
        System.out.println(envioDTO.toString());
        Envio nuevoEnvio = envioDAO.insertarEnvio(envioDTO);
        if (nuevoEnvio != null) {
            return ResponseEntity.status(201).body(nuevoEnvio);
        } else {
            return ResponseEntity.badRequest().body("Error al crear el envío");
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
