package mx.tecnm.backend.api.models;

import java.util.UUID;

public record Pedido (int id,String fecha,double importe_productos,double importe_envio,int usuarios_id,int metodos_pago_id,String fecha_hora_pago,double importe_iva,double total, UUID numero){
}
