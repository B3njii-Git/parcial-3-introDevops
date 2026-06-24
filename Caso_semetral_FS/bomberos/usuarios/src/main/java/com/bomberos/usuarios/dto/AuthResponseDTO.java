package com.bomberos.usuarios.dto;

public class AuthResponseDTO {
    private String email;
    private String rol;
    private String token; // <-- Aquí viaja la Credencial Digital

    public AuthResponseDTO(String email, String rol, String token) {
        this.email = email;
        this.rol = rol;
        this.token = token;
    }
    // Getters
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public String getToken() { return token; }
}