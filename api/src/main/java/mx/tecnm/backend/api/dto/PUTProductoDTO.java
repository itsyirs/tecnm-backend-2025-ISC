package mx.tecnm.backend.api.dto;

public record PUTProductoDTO (String nombre, float precio, String sku,String color, String marca,String descripcion,float peso,float alto,float ancho,float profundidad) {}
