package mx.tecnm.backend.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mx.tecnm.backend.api.models.Productos;
@RequestMapping("/test")
@RestController
public class Test 
{
 @GetMapping("/hello")
 public String helloWorld()
 {
    return "Hola API Rest";
 }
  @GetMapping("/producto")
 public Productos[] getProducto()
 {
    Productos p = new Productos();
    p.nombre="Coca Cola";
    p.precio=15.5;
    p.codigoBarras="1234567890123";
    return new Productos[]{p};
 }
 @GetMapping("/productos")
 public Productos[] getProductos()
 {
    Productos p = new Productos();
    p.nombre="Coca Cola";
    p.precio=15.5;
    p.codigoBarras="1234567890123";
    Productos p2 = new Productos();
    p2.nombre="Pepsi";
    p2.precio=14.0;
    p2.codigoBarras="1234567890124";
    Productos p3 = new Productos();
    p3.nombre="Fanta";
    p3.precio=13.0;
    p3.codigoBarras="1234567890125";
    return new Productos[]{p, p2, p3};
 }
 @GetMapping("/productos/{id}")
 public ResponseEntity<Productos> buscarPorId(@PathVariable int id)
 {
   if(id<0||id>2) return ResponseEntity.notFound().build();
   Productos p = new Productos();
    p.nombre="Coca Cola";
    p.precio=15.5;
    p.codigoBarras="1234567890123";
    Productos p2 = new Productos();
    p2.nombre="Pepsi";
    p2.precio=14.0;
    p2.codigoBarras="1234567890124";
    Productos p3 = new Productos();
    p3.nombre="Fanta";
    p3.precio=13.0;
    p3.codigoBarras="1234567890125";
    Productos [] productos = {p, p2, p3};
    return ResponseEntity.ok(productos[id]);
 }
}
