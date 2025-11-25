package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Producto;
import mx.tecnm.backend.api.repository.ProductoDAO;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoDAO productoDAO;
    @GetMapping()
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> resultado = productoDAO.obtenerProductos();
        return ResponseEntity.ok(resultado);
    }
}
