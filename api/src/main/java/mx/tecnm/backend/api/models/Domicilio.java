package mx.tecnm.backend.api.models;

public record Domicilio (int id,String calle,String numero, String colonia, char[] cp,String estado,String ciudad,int usuarios_id ){

}
