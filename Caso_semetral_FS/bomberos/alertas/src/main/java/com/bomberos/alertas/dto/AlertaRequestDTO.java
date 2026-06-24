package com.bomberos.alertas.dto;

import jakarta.validation.constraints.NotBlank;

public class AlertaRequestDTO {

    @NotBlank(message = "El tipo de alerta es obligatorio")
    private String tipoAlerta;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    @NotBlank(message = "La severidad es obligatoria")
    private String severidad;

    // Getters y Setters
    public String getTipoAlerta() { return tipoAlerta; }
    public void setTipoAlerta(String tipoAlerta) { this.tipoAlerta = tipoAlerta; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getSeveridad() { return severidad; }
    public void setSeveridad(String severidad) { this.severidad = severidad; }
}