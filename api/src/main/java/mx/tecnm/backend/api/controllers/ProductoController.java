package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.dto.ProductoDTO;
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
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        Producto producto = productoDAO.obtenerProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> insertarProducto(@RequestBody ProductoDTO producto)
    {
        Producto productoInsertado = productoDAO.insertarProducto(producto);
        if (productoInsertado != null) {
            return ResponseEntity.status(201).body(productoInsertado);
        } else {
            return ResponseEntity.status(500).body("Error al agregar el producto");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarProducto(@PathVariable int id) {
        Producto productoDesactivado = productoDAO.desactivarProducto(id);
        if (productoDesactivado != null) {
            return ResponseEntity.ok(productoDesactivado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
