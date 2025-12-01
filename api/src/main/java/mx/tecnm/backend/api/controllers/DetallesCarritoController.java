package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.DetallesCarrito;
import mx.tecnm.backend.api.repository.DetallesCarritoDAO;

@RestController
@RequestMapping("/apidetalles-carrito")
public class DetallesCarritoController {
    @Autowired
    private DetallesCarritoDAO detallesCarritoDAO;
    @GetMapping
    public ResponseEntity<List<DetallesCarrito>> obtenerDetallesCarrito() {
        List<DetallesCarrito> resultado = detallesCarritoDAO.obtenerDetallesCarrito();
        return ResponseEntity.ok(resultado);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DetallesCarrito> obtenerPorId(int id) {
        DetallesCarrito detalleCarrito = detallesCarritoDAO.obtenerPorId(id);
        if (detalleCarrito != null) {
            return ResponseEntity.ok(detalleCarrito);
        } else {
            return ResponseEntity.notFound().build();
        }
    }   
    @PostMapping
    public ResponseEntity<DetallesCarrito> insertarDetalleCarrito(@RequestBody DetallesCarrito detalleCarrito) {
        DetallesCarrito nuevoDetalle = detallesCarritoDAO.insertarDetalleCarrito(detalleCarrito);
        if(nuevoDetalle == null){
            return ResponseEntity.status(201).body(nuevoDetalleCarrito);
        }else{
            return ResponseEntity.status(500).build("Error al agregar el detalle del carrito");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DetallesCarrito> desactivarDetalleCarrito(@PathVariable int id, @RequestBody DetallesCarrito detalleCarrito) {
        DetallesCarrito detalleCarritoDesactivado = detallesCarritoDAO.desactivarDetalleCarrito(id, detalleCarrito);
        if (detalleCarritoDesactivado != null) {
            return ResponseEntity.ok(detalleCarritoDesactivado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<DetallesCarrito> actualizarDetalleCarrito(@PathVariable int id, @RequestBody DetallesCarrito detalleCarrito) {
        DetallesCarrito detalleCarritoActualizado = detallesCarritoDAO.actualizarDetalleCarrito(id, detalleCarrito);
        if (detalleCarritoActualizado != null) {
            return ResponseEntity.ok(detalleCarritoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}    