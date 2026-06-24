package com.bomberos.historial.dto;

import java.time.LocalDateTime;

public class HistorialResponseDTO {
    private Long id;
    private String ubicación;
    private String causaProbable;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double hectareasAfectadas;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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