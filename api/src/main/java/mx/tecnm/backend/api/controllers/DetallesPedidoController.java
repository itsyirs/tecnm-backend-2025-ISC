package mx.tecnm.backend.api.controllers;

import java.beans.ConstructorProperties;
import java.lang.module.ResolutionException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.DetallesPedido;
import mx.tecnm.backend.api.repository.DetallesPedidoDAO;

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
    public ResponseEntity<DetallesPedido> obtenerPorId(int id) {
        DetallesPedido detallePedido = detallesPedidoDAO.obtenerPorId(id);
        if (detallePedido != null) {
            return ResponseEntity.ok(detallePedido);
        } else {        
            return ResponseEntity.notFound().build();
        }
    }   
    @PostMapping
    public ResponseEntity<DetallesPedido> insertarDetallePedido(@RequestBody DetallesPedido detallePedido) {
        DetallesPedido nuevoDetalle = detallesPedidoDAO.insertarDetallePedido(detallePedido);
        if(nuevoDetalle != null){
            return ResponseEntity.status(201).body(nuevoDetalle);
        }else{
            return ResponseEntity.status(500).build("Error al agregar el detalle del pedido");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DetallesPedido> desactivarDetallePedido(@PathVariable int id) {
        DetallesPedido detallePedidoDesactivado = detallesPedidoDAO.desactivarDetallePedido(id);
        if (detallePedidoDesactivado != null) {
            return ResponseEntity.ok(detallePedidoDesactivado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<DetallesPedido> actualizarDetallePedido(@PathVariable int id, @RequestBody DetallesPedido detallePedido) {
        DetallesPedido detallePedidoActualizado = detallesPedidoDAO.actualizarDetallePedido(id, detallePedido);
        if (detallePedidoActualizado != null) {
            return ResponseEntity.ok(detallePedidoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }   
    @PostMapping
    public ResponseEntity<DetallesPedido> agregarProductoAlCarrito(int usuario_id, int producto_id, int cantidadAgregar, double precioUnitario) {
        DetallesPedido nuevoDetalle = detallesPedidoDAO.agregarProductoAlCarrito(usuario_id, producto_id, cantidadAgregar, precioUnitario);
        if(nuevoDetalle != null){
            return ResponseEntity.status(201).body(nuevoDetalle);
        }else{
            return ResponseEntity.status(500).build("Error al agregar el producto al carrito");
        }
    }
    @DeleteMapping("/{id}")
        public ResponseEntity<DetallesPedido> quitarProductoDelCarrito(@PathVariable int id) {
        boolean exito = detallesPedidoDAO.quitarProductoDelCarrito(id);
        if (exito) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/generar-pedido")
    public ResponseEntity<DetallesPedido> generarPedido(@RequestBody int usuarioId) {
        DetallesPedido pedidoGenerado = detallesPedidoDAO.generarPedido(usuarioId);
        if (pedidoGenerado != null) {
            return ResponseEntity.status(201).body(pedidoGenerado);
        } else {
            return ResponseEntity.status(500).build("Error al generar el pedido");
        }
    }
}