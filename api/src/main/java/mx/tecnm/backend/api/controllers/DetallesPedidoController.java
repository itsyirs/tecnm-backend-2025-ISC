package mx.tecnm.backend.api.controllers;

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
}
