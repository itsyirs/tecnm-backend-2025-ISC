package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Domicilio;
import mx.tecnm.backend.api.dto.DomicilioDTO;
@Repository
public class DomicilioDAO {
    @Autowired
    private JdbcClient jdbcClient;
    public List<Domicilio> obtenerDomicilios() {
        String sql = "SELECT id, calle, numero, colonia, cp, estado, ciudad, usuarios_id FROM domicilios";
        return jdbcClient.sql(sql)
                .query(new DomicilioRM())
                .list();
    }
    //insertar domicilio
    public Domicilio insertarDomicilio(DomicilioDTO domicilio) {

        String sql = """
                INSERT INTO domicilios (calle, numero, colonia, ciudad, estado, usuarios_id)
                VALUES (:calle, :numero, :colonia, :ciudad, :estado, :usuarioId)
                RETURNING id, calle, numero, colonia, ciudad, estado, activo, usuarios_id
                """;

        return jdbcClient.sql(sql)
                .param("calle", domicilio.calle())
                .param("numero", domicilio.numero())
                .param("colonia", domicilio.colonia())
                .param("ciudad", domicilio.ciudad())
                .param("estado", domicilio.estado())
                .param("usuarioId", domicilio.usuarios_id())
                .query(new DomicilioRM())
                .single();
    }
    //buscar por id
    public Domicilio obtenerDomiciliosPorId(int id) {
        String sql = "SELECT id, calle, numero, colonia, ciudad, estado, activo, usuarios_id FROM domicilios WHERE id = :id";
        List<Domicilio> lista = jdbcClient.sql(sql)
                .param("id", id)
                .query(new DomicilioRM())
                .list();

        return lista.isEmpty() ? null : lista.get(0);
    }

    //actualizar domicilio
   public Domicilio actualizarDomicilio(int id, DomicilioDTO domicilio) {

        String sql = """
                UPDATE domicilios SET
                    calle = :calle,
                    numero = :numero,
                    colonia = :colonia,
                    ciudad = :ciudad,
                    estado = :estado
                WHERE id = :id
                RETURNING id, calle, numero, colonia, ciudad, estado, activo, usuarios_id
                """;

        return jdbcClient.sql(sql)
                .param("id", domicilio.id())
                .param("calle", domicilio.calle())
                .param("numero", domicilio.numero())
                .param("colonia", domicilio.colonia())
                .param("ciudad", domicilio.ciudad())
                .param("estado", domicilio.estado())
                .query(new DomicilioRM())
                .single();
    } 

    public Domicilio desactivarDomicilio(int id) {

        String sql = """
                UPDATE domicilios SET activo = false
                WHERE id = :id
                """;

        int filas = jdbcClient.sql(sql)
            .param("id", id)
            .update();
        return filas > 0 ? obtenerDomiciliosPorId(id) : null;
    }
    
}
