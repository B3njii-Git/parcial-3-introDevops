package com.bomberos.alertas.controller;

import com.bomberos.alertas.dto.AlertaRequestDTO;
import com.bomberos.alertas.dto.AlertaResponseDTO;
import com.bomberos.alertas.service.AlertasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que gestiona la emisión y administración de alertas del sistema.
 * Provee endpoints para consultar, crear y eliminar alertas de emergencias o evacuaciones.
 */
@RestController
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertasService alertaService;

    /**
     * Obtiene el listado completo de todas las alertas registradas y activas en la plataforma.
     *
     * @return Una lista de objetos AlertaResponseDTO con los detalles de cada alerta.
     */
    @GetMapping
    public List<AlertaResponseDTO> obtenerAlertas() {
        return alertaService.obtenerAlertas();
    }

    /**
     * Crea y difunde una nueva alerta en el sistema (ej. advertencias meteorológicas o incendios cercanos).
     *
     * @param requestDTO Objeto que contiene los datos de la alerta (tipo, mensaje y severidad).
     * @return Objeto AlertaResponseDTO con los datos de la alerta recién guardada.
     */
    @PostMapping
    public AlertaResponseDTO crearAlerta(@Valid @RequestBody AlertaRequestDTO requestDTO) {
        return alertaService.crearAlerta(requestDTO);
    }

    /**
     * Elimina una alerta específica del sistema basándose en su identificador único.
     *
     * @param id El identificador (Long) de la alerta que se desea eliminar.
     */
    @DeleteMapping("/{id}")
    public void eliminarAlerta(@PathVariable Long id) {
        alertaService.eliminarAlerta(id);
    }
}