package mx.tecnm.backend.api.dto;

import java.time.LocalDate;

import mx.tecnm.backend.api.models.Sexo;

public record UsuarioDTO(String nombre,String email,String telefono,Sexo sexo,LocalDate fecha_nacimiento,String contrasena) { }