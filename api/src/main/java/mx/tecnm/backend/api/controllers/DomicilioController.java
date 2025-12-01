package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Domicilio;
import mx.tecnm.backend.api.repository.DomicilioDAO;

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
    public ResponseEntity<Domicilio> obtenerPorId(int id) {
        Domicilio domicilio = domicilioDAO.obtenerPorId(id);
        if (domicilio != null) {
            return ResponseEntity.ok(domicilio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Domicilio> insertarDomicilio(@RequestBody Domicilio domicilio) {
        Domicilio nuevoDomicilio = domicilioDAO.insertarDomicilio(domicilio);  
        if(nuevoDomicilio != null){
            return ResponseEntity.status(201).body(nuevoDomicilio);
        }else{
            return ResponseEntity.status(500).build("Error al agregar el domicilio");
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
    public ResponseEntity<Domicilio> actualizarDomicilio(@PathVariable int id, @RequestBody Domicilio domicilio) {
        Domicilio domicilioActualizado = domicilioDAO.actualizarDomicilio(id, domicilio);
        if (domicilioActualizado != null) {
            return ResponseEntity.ok(domicilioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}