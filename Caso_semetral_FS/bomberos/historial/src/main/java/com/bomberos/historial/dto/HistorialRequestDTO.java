package com.bomberos.historial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class HistorialRequestDTO {

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicación;

    @NotBlank(message = "La causa probable no puede estar vacía")
    private String causaProbable;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @NotNull(message = "Debe registrar las hectáreas afectadas")
    private Double hectareasAfectadas;

    // Getters y Setters
    public String getUbicación() { return ubicación; }
    public void setUbicación(String ubicación) { this.ubicación = ubicación; }
    public String getCausaProbable() { return causaProbable; }
    public void setCausaProbable(String causaProbable) { this.causaProbable = causaProbable; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }
    public Double getHectareasAfectadas() { return hectareasAfectadas; }
    public void setHectareasAfectadas(Double hectareasAfectadas) { this.hectareasAfectadas = hectareasAfectadas; }
}