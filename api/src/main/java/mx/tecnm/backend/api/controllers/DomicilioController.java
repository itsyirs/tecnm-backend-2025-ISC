package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Domicilio;
import mx.tecnm.backend.api.repository.DomicilioDAO;
import mx.tecnm.backend.api.dto.DomicilioDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController {
    @Autowired
    private DomicilioDAO domicilioDAO;
    @GetMapping()
    public ResponseEntity<List<Domicilio>> obtenerDomicilios() {
        List<Domicilio> resultado = domicilioDAO.obtenerDomicilios();
        return ResponseEntity.ok(resultado);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> obtenerDomiciliosPorId(@PathVariable int id) {
        Domicilio domicilio = domicilioDAO.obtenerDomiciliosPorId(id);
        if (domicilio != null) {
            return ResponseEntity.ok(domicilio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> insertarDomicilio(@RequestBody DomicilioDTO domicilio) {
        Domicilio nuevoDomicilio = domicilioDAO.insertarDomicilio(domicilio);  
        if(nuevoDomicilio != null){
            return ResponseEntity.status(201).body(nuevoDomicilio);
        }else{
            return ResponseEntity.status(500).body("Error al agregar el domicilio");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Domicilio> desactivarDomicilio(@PathVariable int id) {
        Domicilio domicilioDesactivado = domicilioDAO.desactivarDomicilio(id);
        if (domicilioDesactivado != null) {
            return ResponseEntity.ok(domicilioDesactivado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Domicilio> actualizarDomicilio(@PathVariable int id, @RequestBody DomicilioDTO domicilio) {
        Domicilio domicilioActualizado = domicilioDAO.actualizarDomicilio(id, domicilio);
        if (domicilioActualizado != null) {
            return ResponseEntity.ok(domicilioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}