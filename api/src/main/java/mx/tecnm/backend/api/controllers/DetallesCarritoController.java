package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}
