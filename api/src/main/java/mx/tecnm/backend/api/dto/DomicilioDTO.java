package mx.tecnm.backend.api.dto;

public record DomicilioDTO(int id,String calle,String numero, String colonia, char[] cp,String estado,String ciudad,int usuarios_id ) {

}
