package mx.tecnm.backend.api.models;

public record Envio (int id,String fecha_entrega,String fecha_envio,EstadoEnvio estado,String numero_seguimiento,int domicilios_id,int pedidos_id){

}
