package com.bomberos.monitoreo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "zonas_monitoreo")
public class ZonaMonitoreo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la zona es obligatorio")
    private String nombreZona;

    @NotNull(message = "La latitud es obligatoria")
    private Double latitud;

    @NotNull(message = "La longitud es obligatoria")
    private Double longitud;

    @NotBlank(message = "El nivel de riesgo es obligatorio (ej. ALTO, MEDIO, BAJO)")
    private String nivelRiesgo;

    @NotNull(message = "Debe indicar si la brigada está activa (true/false)")
    private Boolean brigadaActiva;

    public ZonaMonitoreo() {}

    // Getters y Setters exactos a los tuyos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreZona() { return nombreZona; }
    public void setNombreZona(String nombreZona) { this.nombreZona = nombreZona; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    public String getNivelRiesgo() { return nivelRiesgo; }
    public void setNivelRiesgo(String nivelRiesgo) { this.nivelRiesgo = nivelRiesgo; }
    public Boolean getBrigadaActiva() { return brigadaActiva; }
    public void setBrigadaActiva(Boolean brigadaActiva) { this.brigadaActiva = brigadaActiva; }
}