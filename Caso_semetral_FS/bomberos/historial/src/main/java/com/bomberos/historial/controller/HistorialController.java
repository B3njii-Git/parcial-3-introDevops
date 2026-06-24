package com.bomberos.historial.controller;

import com.bomberos.historial.dto.HistorialRequestDTO;
import com.bomberos.historial.dto.HistorialResponseDTO;
import com.bomberos.historial.service.HistorialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de la bitácora e historial de emergencias.
 * Provee los endpoints necesarios para registrar y consultar los eventos pasados atendidos por las brigadas.
 */
@RestController
@RequestMapping("/historial")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    /**
     * Recupera el listado completo del historial de incidentes y emergencias atendidas.
     * * @return Una lista de objetos HistorialResponseDTO con los detalles de cada evento registrado.
     */
    @GetMapping
    public List<HistorialResponseDTO> listar() {
        return historialService.listar();
    }

    /**
     * Registra un nuevo evento en el historial del sistema tras la finalización de una emergencia.
     * * @param requestDTO Objeto DTO que contiene los detalles del evento (fecha, descripción, recursos utilizados).
     * @return Objeto HistorialResponseDTO con el registro persistido exitosamente y su identificador.
     */
    @PostMapping
    public HistorialResponseDTO guardar(@Valid @RequestBody HistorialRequestDTO requestDTO) {
        return historialService.guardar(requestDTO);
    }
}