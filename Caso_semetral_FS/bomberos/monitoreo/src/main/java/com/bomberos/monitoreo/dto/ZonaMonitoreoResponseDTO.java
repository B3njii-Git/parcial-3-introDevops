package com.bomberos.monitoreo.dto;

public class ZonaMonitoreoResponseDTO {
    private Long id;
    private String nombreZona;
    private Double latitud;
    private Double longitud;
    private String nivelRiesgo;
    private Boolean brigadaActiva;

    // Getters y Setters
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