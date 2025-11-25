package mx.tecnm.backend.api.models;
public record Usuario(int id, String nombre, String email, char[] telefono, Sexo sexo, String fecha_nacimiento, String contrasena, String fecha_registro) {
}
