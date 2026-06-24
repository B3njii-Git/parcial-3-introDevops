package com.bomberos.reportes.controller;

import com.bomberos.reportes.dto.ReporteRequestDTO;
import com.bomberos.reportes.dto.ReporteResponseDTO;
import com.bomberos.reportes.model.Reporte;
import com.bomberos.reportes.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.bomberos.reportes.messaging.ReporteProducer;

/**
 * Controlador REST que expone los endpoints para la gestión de reportes de incidentes.
 * Permite a la plataforma centralizar las alertas y denuncias ciudadanas sobre focos de incendios.
 */
@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteProducer reporteProducer;

    /**
     * Obtiene el listado histórico y actual de todos los reportes de incidentes registrados.
     *
     * @return Una lista de objetos ReporteResponseDTO con la información detallada de los reportes.
     */
    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> listarReportes() {
        return ResponseEntity.ok(reporteService.obtenerTodos());
    }

    /**
     * Registra un nuevo reporte de incendio o emergencia en el sistema.
     * Mapea los datos del formulario ciudadano ingresados desde el Frontend.
     *
     * @param *requestDTO Objeto DTO que contiene los datos de entrada del reporte (ubicación, tipo, descripción).
     * @return Objeto ReporteResponseDTO con los datos del reporte guardado y su ID generado.
     */
    @PostMapping
    public ResponseEntity<ReporteResponseDTO> crearReporte(@RequestBody ReporteRequestDTO request) {
        ReporteResponseDTO response = reporteService.crearReporte(request);
        reporteProducer.enviarReporteCreado("Se ha creado un nuevo reporte: " + response.toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable String id) {
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> actualizarReporte(@PathVariable String id, @RequestBody ReporteRequestDTO requestDTO) {
        ReporteResponseDTO actualizado = reporteService.actualizarReporte(id, requestDTO);
        return ResponseEntity.ok(actualizado);
    }
}