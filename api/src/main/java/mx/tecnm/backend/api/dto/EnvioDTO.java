package mx.tecnm.backend.api.dto;

import mx.tecnm.backend.api.models.EstadoEnvio;

public record EnvioDTO(String fecha_entrega,String fecha_envio,EstadoEnvio estado,String numero_seguimiento,int domicilios_id,int pedidos_id) {}
