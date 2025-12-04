package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.DetallesCarrito;
import mx.tecnm.backend.api.models.Pedido;
import mx.tecnm.backend.api.dto.DetallesCarritoDTO;
import mx.tecnm.backend.api.dto.DetallesPedidoDTO;
import mx.tecnm.backend.api.dto.UsuarioIdDTO;
import mx.tecnm.backend.api.repository.DetallesCarritoDAO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/detalles-carrito")
public class DetallesCarritoController {
    @Autowired
    private DetallesCarritoDAO detallesCarritoDAO;
    @GetMapping
    public ResponseEntity<List<DetallesCarrito>> obtenerDetallesCarrito() {
        List<DetallesCarrito> resultado = detallesCarritoDAO.obtenerDetallesCarrito();
        return ResponseEntity.ok(resultado);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DetallesCarrito> obtenerDetallesCarritoPorId(@PathVariable int id) {
        DetallesCarrito detalleCarrito = detallesCarritoDAO.obtenerDetallesCarritoPorId(id);
        if (detalleCarrito != null) {
            return ResponseEntity.ok(detalleCarrito);
        } else {
            return ResponseEntity.notFound().build();
        }
    }  

    @PostMapping
    public ResponseEntity<?> insertarDetallesCarrito(@RequestBody DetallesCarritoDTO detallesCarrito) {
        DetallesCarrito nuevoDetallesCarrito = detallesCarritoDAO.insertarDetallesCarrito(detallesCarrito);
        if(nuevoDetallesCarrito == null){
            return ResponseEntity.status(201).body(nuevoDetallesCarrito);
        }else{
            return ResponseEntity.status(500).body("Error al agregar el detalle del carrito");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DetallesCarrito> desactivarDetallesCarrito(@PathVariable int id) {
        DetallesCarrito detallesCarritoDesactivado = detallesCarritoDAO.desactivarDetallesCarrito(id);
        if (detallesCarritoDesactivado != null) {
            return ResponseEntity.ok(detallesCarritoDesactivado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DetallesCarrito> actualizarDetallesCarrito(@PathVariable int id, @RequestBody DetallesCarritoDTO detalleCarrito) {
        DetallesCarrito detalleCarritoActualizado = detallesCarritoDAO.actualizarDetallesCarrito(id, detalleCarrito);
        if (detalleCarritoActualizado != null) {
            return ResponseEntity.ok(detalleCarritoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 
    
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProductoAlCarrito(@RequestBody DetallesPedidoDTO dto, int usuario_id, int producto_id, int cantidadAgregar,double precioUnitario) {
            DetallesCarrito nuevoDetalle = detallesCarritoDAO.agregarProductoAlCarrito(dto,usuario_id,producto_id,cantidadAgregar,precioUnitario);
        if(nuevoDetalle != null){
            return ResponseEntity.status(201).body(nuevoDetalle);
        } else {
            return ResponseEntity.status(500).body("Error al agregar producto: ");
        }
    }
    
    @DeleteMapping("/quitar/{id}")
    public ResponseEntity<?> quitarProductoDelCarrito(@PathVariable int id) {
        boolean exito = detallesCarritoDAO.quitarProductoDelCarrito(id);
        if (exito) {
            return ResponseEntity.ok("Producto eliminado del carrito");
        } else {
            return ResponseEntity.status(500).body("No se encontró el producto en el carrito");
        }
    }

    @PostMapping("/generar-pedido")
    public ResponseEntity<?> generarPedido(@RequestBody UsuarioIdDTO dto) {

        Pedido pedidoGenerado = detallesCarritoDAO.generarPedido(dto.UsuarioId());

        if (pedidoGenerado != null) {
            return ResponseEntity.status(201).body(pedidoGenerado);
        } else {
            return ResponseEntity.status(500).body("El carrito está vacío. No se puede generar el pedido");
        }
    }
}
     