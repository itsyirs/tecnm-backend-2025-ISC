package mx.tecnm.backend.api.dto;

public record DetallesPedidoDTO(int pedidos_id, int productos_id, int cantidad, double precio) {}