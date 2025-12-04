package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.MetodoPago;
import mx.tecnm.backend.api.dto.MetodoPagoDTO;

@Repository
public class MetodoPagoDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<MetodoPago> obtenerMetodosPago() {
        String sql = "SELECT id, nombre, comision FROM metodos_pago where activo=true)";
        return jdbcClient.sql(sql)
                .query(new MetodoPagoRW())
                .list();
    }
    public MetodoPago obtenerMetodoPagoPorId(int id) {
        String sql = """
            SELECT id, nombre, comision
            FROM metodos_pago
            WHERE id = :id and activo=true""";

        List<MetodoPago> lista = jdbcClient.sql(sql)
                .param("id", id)
                .query(new MetodoPagoRW())
                .list();

        return lista.isEmpty() ? null : lista.get(0);
    }
    public MetodoPago insertarMetodoPago(MetodoPagoDTO dto) {
        int nuevoId = jdbcClient.sql("""
                INSERT INTO metodos_pago(nombre, comision)
                VALUES (:nombre, :comision)
                RETURNING id
            """)
            .param("nombre", dto.nombre())
            .param("comision", dto.comision())
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return obtenerMetodoPagoPorId(nuevoId);
    }
    
    public MetodoPago actualizarMetodoPago(int id, MetodoPagoDTO dto) {
        int filas = jdbcClient.sql("""
                UPDATE metodos_pago
                SET nombre = :nombre,
                    comision = :comision
                WHERE id = :id
                RETURNING id
            """)
            .param("id", id)
            .param("nombre", dto.nombre())
            .param("comision", dto.comision())
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return filas > 0 ? obtenerMetodoPagoPorId(id) : null;
    }
    
    public MetodoPago desactivarMetodoPago(int id) {
        int filas = jdbcClient.sql("""
                UPDATE metodos_pago
                SET activo = false
                WHERE id = :id
            """)
            .param("id", id)
            .query((rs,rowNum) -> rs.getInt("id"))
            .single();

        return filas > 0 ? obtenerMetodoPagoPorId(id) : null;
    }

}