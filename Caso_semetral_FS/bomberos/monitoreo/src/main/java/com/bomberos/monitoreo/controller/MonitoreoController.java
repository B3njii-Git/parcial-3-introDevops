package com.bomberos.monitoreo.controller;

import com.bomberos.monitoreo.dto.ZonaMonitoreoRequestDTO;
import com.bomberos.monitoreo.dto.ZonaMonitoreoResponseDTO;
import com.bomberos.monitoreo.service.MonitoreoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de la gestión y visualización de las zonas de monitoreo.
 * Permite a la plataforma administrar las áreas geográficas y sus respectivos niveles de riesgo de incendio.
 */
@RestController
@RequestMapping("/monitoreo")
public class MonitoreoController {

    @Autowired
    private MonitoreoService monitoreoService;

    /**
     * Obtiene el listado completo de todas las zonas de monitoreo registradas en el sistema.
     * Ideal para renderizar los marcadores de riesgo en el mapa del Frontend.
     *
     * @return Una lista de objetos ZonaMonitoreoResponseDTO con la información espacial y de riesgo de cada zona.
     */
    @GetMapping
    public List<ZonaMonitoreoResponseDTO> listarTodo() {
        return monitoreoService.listarTodo();
    }

    /**
     * Registra una nueva zona de monitoreo en la base de datos municipal.
     * Valida los datos de entrada para asegurar que las coordenadas y el nivel de riesgo sean correctos.
     *
     * @param requestDTO Objeto DTO con los datos de la zona (nombre, latitud, longitud, nivel de riesgo y brigada asignada).
     * @return Objeto ZonaMonitoreoResponseDTO con la zona exitosamente guardada y su ID generado.
     */
    @PostMapping
    public ZonaMonitoreoResponseDTO guardar(@Valid @RequestBody ZonaMonitoreoRequestDTO requestDTO) {
        return monitoreoService.guardar(requestDTO);
    }
}