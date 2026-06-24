package com.bomberos.historial.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_incendios")
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicación;

    @NotBlank(message = "La causa probable no puede estar vacía")
    private String causaProbable;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @NotNull(message = "Debe registrar las hectáreas afectadas")
    private Double hectareasAfectadas;

    public Historial() {}

    // Getters y Setters Manuales
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