package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.dto.PUTPedidoDTO;
import mx.tecnm.backend.api.dto.PedidoDTO;
import mx.tecnm.backend.api.models.Pedido;
import mx.tecnm.backend.api.repository.PedidoDAO;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoDAO pedidoDAO;
    @GetMapping()
    public ResponseEntity<List<Pedido>> obtenerPedidos() {
        List<Pedido> resultado = pedidoDAO.obtenerPedidos();
        return ResponseEntity.ok(resultado);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable int id) {
        Pedido pedido = pedidoDAO.obtenerPedidoPorId(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> insertarPedido(@RequestBody PedidoDTO pedido)
    {
            System.out.println("DTO recibido: " + pedido);
        Pedido pedidoInsertado = pedidoDAO.insertarPedido(pedido);
        if (pedidoInsertado != null) {
            return ResponseEntity.status(201).body(pedidoInsertado);
        } else {
            return ResponseEntity.status(500).body("Error al agregar el pedido");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarPedido(@PathVariable int id) {
        Pedido pedidoDesactivado = pedidoDAO.desactivarPedido(id);
        if (pedidoDesactivado != null) {
            return ResponseEntity.ok(pedidoDesactivado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPedido(@PathVariable int id, @RequestBody PUTPedidoDTO pedido) {
        Pedido pedidoActualizado = pedidoDAO.actualizarPedido(id, pedido);
        if (pedidoActualizado != null) {
            return ResponseEntity.ok(pedidoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
