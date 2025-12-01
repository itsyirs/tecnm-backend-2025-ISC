package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.MetodoPago;

@Repository
public class MetodoPagoDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<mx.tecnm.backend.api.models.MetodoPago> obtenerMetodosPago() {
        String sql = "SELECT id, nombre, comision FROM metodos_pago where activo=true)";
        return jdbcClient.sql(sql)
                .query(new MetodoPagoRW())
                .list();
    }
    public MetodoPago obtenerPorId(int id) {
        String sql = """
            SELECT id, nombre, comision
            FROM metodos_pago
            WHERE id = :id and activo=true""";

        List<mx.tecnm.backend.api.models.MetodoPago> lista = jdbcClient.sql(sql)
                .param("id", id)
                .query(new MetodoPagoRW())
                .list();

        return lista.isEmpty() ? null : lista.get(0);
    }
    public MetodoPago insertar(MetodoPago dto) {
        int nuevoId = jdbcClient.sql("""
                INSERT INTO metodos_pago(nombre, comision)
                VALUES (:nombre, :comision)
                RETURNING id
            """)
            .param("nombre", dto.getNombre())
            .param("comision", dto.getComision())
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return obtenerPorId(nuevoId);
    }
    public MetodoPago actualizar(int id, MetodoPago dto) {
        int filas = jdbcClient.sql("""
                UPDATE metodos_pago
                SET nombre = :nombre,
                    comision = :comision
                WHERE id = :id
                RETURNING id
            """)
            .param("id", id)
            .param("nombre", dto.getNombre())
            .param("comision", dto.getComision())
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return filas > 0 ? obtenerPorId(id) : null;
    }
    @DeleteMapping("/{id}")
    public boolean desactivar(int id) {
        int filas = jdbcClient.sql("""
                UPDATE metodos_pago
                SET activo = false
                WHERE id = :id
            """)
            .param("id", id)
            .update();

        return filas > 0;
    }
}