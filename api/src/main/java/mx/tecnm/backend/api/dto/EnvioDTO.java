package mx.tecnm.backend.api.dto;

import java.time.LocalDate;


import mx.tecnm.backend.api.models.EstadoEnvio;

public record EnvioDTO(LocalDate fecha_entrega,LocalDate fecha_envio, EstadoEnvio estado, Integer domicilios_id, Integer pedidos_id) {}
