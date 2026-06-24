package com.bomberos.usuarios.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UsuarioResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // Transformaremos el UUID a String
    private String nombre;
    private String email;
    private String telefono;
    private String rol;
    private LocalDateTime fechaRegistro;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}