package mx.tecnm.backend.api.dto;

import mx.tecnm.backend.api.models.EstadoEnvio;

public record PUTEnvioDTO(String fecha_entrega,String fecha_envio,EstadoEnvio estado,Integer domicilios_id,Integer pedidos_id) {}
