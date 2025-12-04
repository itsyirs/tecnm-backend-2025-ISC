package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.MetodoPago;
import mx.tecnm.backend.api.repository.MetodoPagoDAO;
import mx.tecnm.backend.api.dto.MetodoPagoDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/metodos-pago")
public class MetodoPagoController {
    @Autowired
    private MetodoPagoDAO metodoPagoDAO;
    @GetMapping()
    public ResponseEntity<List<MetodoPago>> obtenerMetodosPago() {
        List<MetodoPago> resultado = metodoPagoDAO.obtenerMetodosPago();
        return ResponseEntity.ok(resultado);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> obtenerMetodoPagoPorId(int id) {
        MetodoPago metodoPago = metodoPagoDAO.obtenerMetodoPagoPorId(id);
        if (metodoPago != null) {
            return ResponseEntity.ok(metodoPago);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> insertarMetodoPago(@RequestBody MetodoPagoDTO metodoPago) {
        MetodoPago nuevoMetodoPago = metodoPagoDAO.insertarMetodoPago(metodoPago);  
        if(nuevoMetodoPago != null){
            return ResponseEntity.status(201).body(nuevoMetodoPago);
        }else{
            return ResponseEntity.status(500).body("error");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<MetodoPago> desactivarMetodoPago(@PathVariable int id) {
        MetodoPago metodoPagoDesactivado = metodoPagoDAO.desactivarMetodoPago(id);
        if (metodoPagoDesactivado != null) {
            return ResponseEntity.ok(metodoPagoDesactivado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> actualizarMetodoPago(@PathVariable int id, @RequestBody MetodoPagoDTO metodoPago) {
        MetodoPago metodoPagoActualizado = metodoPagoDAO.actualizarMetodoPago(id, metodoPago);
        if (metodoPagoActualizado != null) {
            return ResponseEntity.ok(metodoPagoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}