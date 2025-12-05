package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.DetallesCarrito;
import mx.tecnm.backend.api.models.Pedido;
import mx.tecnm.backend.api.dto.AgregarPedidoDTO;
import mx.tecnm.backend.api.dto.PUTDetallesCarritoDTO;
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
        if(nuevoDetallesCarrito != null){
            return ResponseEntity.status(201).body(nuevoDetallesCarrito);
        }else{
            return ResponseEntity.status(500).body("Error al agregar el detalle del carrito");
        }
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> desactivarDetallesCarrito(@RequestBody PUTDetallesCarritoDTO dto) {
        Boolean detallesCarritoDesactivado = detallesCarritoDAO.quitarProductoDelCarrito(dto);
        return ResponseEntity.ok(detallesCarritoDesactivado);
    }
    
    @PutMapping()
    public ResponseEntity<DetallesCarrito> actualizarDetallesCarrito(@RequestBody PUTDetallesCarritoDTO detalleCarrito) {
        DetallesCarrito detalleCarritoActualizado = detallesCarritoDAO.actualizarCantidad(detalleCarrito);
        if (detalleCarritoActualizado != null) {
            return ResponseEntity.ok(detalleCarritoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/generar-pedido")
    public ResponseEntity<?> generarPedido(@RequestBody AgregarPedidoDTO dto) {

        Pedido pedidoGenerado = detallesCarritoDAO.generarPedido(dto);

        if (pedidoGenerado != null) {
            return ResponseEntity.status(201).body(pedidoGenerado);
        } else {
            return ResponseEntity.status(500).body("El carrito está vacío. No se puede generar el pedido");
        }
    }
}
     