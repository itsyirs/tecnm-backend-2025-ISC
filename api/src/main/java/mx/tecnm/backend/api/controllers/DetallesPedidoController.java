package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.dto.DetallesPedidoDTO;
import mx.tecnm.backend.api.dto.PUTDetallesPedidoDTO;
import mx.tecnm.backend.api.models.DetallesPedido;
import mx.tecnm.backend.api.repository.DetallesPedidoDAO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/detalles-pedido")
public class DetallesPedidoController {
    @Autowired
    private DetallesPedidoDAO detallesPedidoDAO;
    @GetMapping()
    public ResponseEntity<List<DetallesPedido>> obtenerDetallesPedidos() {
        List<DetallesPedido> resultado = detallesPedidoDAO.obtenerDetallesPedidos();
        return ResponseEntity.ok(resultado);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DetallesPedido> obtenerDetallesPedidoPorId(@PathVariable int id) {
        DetallesPedido detallePedido = detallesPedidoDAO.obtenerDetallesPedidoPorId(id);
        if (detallePedido != null) {
            return ResponseEntity.ok(detallePedido);
        } else {        
            return ResponseEntity.notFound().build();
        }
    }   
    @PostMapping
    public ResponseEntity<?> insertarDetallesPedido(@RequestBody DetallesPedidoDTO detallePedido) {
        DetallesPedido nuevoDetalle = detallesPedidoDAO.insertarDetallesPedido(detallePedido);
        if(nuevoDetalle != null){
            return ResponseEntity.status(201).body(nuevoDetalle);
        }else{
            return ResponseEntity.status(500).body("Error al agregar el detalle del pedido");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DetallesPedido> desactivarDetallesPedido(@PathVariable int id) {
        DetallesPedido detallePedidoDesactivado = detallesPedidoDAO.desactivarDetallesPedido(id);
        if (detallePedidoDesactivado != null) {
            return ResponseEntity.ok(detallePedidoDesactivado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<DetallesPedido> actualizarDetallesPedido(@PathVariable int id, @RequestBody PUTDetallesPedidoDTO detallePedido) {
        DetallesPedido detallePedidoActualizado = detallesPedidoDAO.actualizarDetallesPedido(id, detallePedido);
        if (detallePedidoActualizado != null) {
            return ResponseEntity.ok(detallePedidoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }   
    
}