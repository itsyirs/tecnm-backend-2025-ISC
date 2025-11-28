package mx.tecnm.backend.api.dto;

public record ProductoDTO (String nombre, float precio, String sku,String color, String marca,String descripcion,float peso,float alto,float ancho,float profundidad,int categorias_id){}
