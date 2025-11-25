package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.MetodoPago;
import mx.tecnm.backend.api.repository.MetodoPagoDAO;

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
}
