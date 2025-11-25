package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Producto;

@Repository
public class ProductoDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<Producto> obtenerProductos() {
        String sql = "SELECT id, nombre, precio, sku, color, marca, descripcion, peso, alto, ancho, profundidad, categorias_id FROM productos";
        return jdbcClient.sql(sql)
                .query(new ProductoRM())
                .list();
    }
}