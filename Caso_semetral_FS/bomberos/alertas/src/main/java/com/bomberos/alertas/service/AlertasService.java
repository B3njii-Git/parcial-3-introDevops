package com.bomberos.alertas.service;

import com.bomberos.alertas.dto.AlertaRequestDTO;
import com.bomberos.alertas.dto.AlertaResponseDTO;
import com.bomberos.alertas.exception.ResourceNotFoundException; // <-- Import de la excepción agregado
import com.bomberos.alertas.model.Alerta;
import com.bomberos.alertas.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que centraliza la lógica de negocio para la gestión de Alertas.
 * Realiza las operaciones de mapeo de datos y comunicación con el repositorio para persistir o consultar las alertas en la base de datos.
 */
@Service
public class AlertasService {

    @Autowired
    private AlertaRepository alertaRepository;

    /**
     * Convierte una entidad Alerta nativa de la base de datos en su correspondiente objeto de transferencia de datos (DTO).
     *
     * @param alerta La entidad Alerta obtenida de la base de datos.
     * @return Un objeto AlertaResponseDTO formateado para ser expuesto de forma segura por el controlador.
     */
    // Mapeador de Entidad a DTO
    private AlertaResponseDTO mapToDTO(Alerta alerta) {
        AlertaResponseDTO dto = new AlertaResponseDTO();
        dto.setId(alerta.getId());
        dto.setTipoAlerta(alerta.getTipoAlerta());
        dto.setMensaje(alerta.getMensaje());
        dto.setSeveridad(alerta.getSeveridad());
        dto.setFechaCreacion(alerta.getFechaCreacion());
        return dto;
    }

    /**
     * Recupera todas las alertas almacenadas en el sistema y las transforman en una lista de DTOs.
     *
     * @return Lista de objetos AlertaResponseDTO.
     */
    public List<AlertaResponseDTO> obtenerAlertas() {
        return alertaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Instancia y persiste una nueva entidad Alerta en la base de datos a partir de los datos recibidos.
     * La fecha de creación se genera automáticamente en la entidad.
     *
     * @param request Objeto AlertaRequestDTO con los datos ingresados para la nueva alerta.
     * @return El AlertaResponseDTO resultante con la alerta persistida exitosamente.
     */
    public AlertaResponseDTO crearAlerta(AlertaRequestDTO request) {
        Alerta alerta = new Alerta();
        alerta.setTipoAlerta(request.getTipoAlerta());
        alerta.setMensaje(request.getMensaje());
        alerta.setSeveridad(request.getSeveridad());
        // No seteamos fechaCreacion, ya que tu Entidad lo hace sola en su constructor

        Alerta guardada = alertaRepository.save(alerta);
        return mapToDTO(guardada);
    }

    /**
     * Elimina una alerta de la base de datos de manera definitiva utilizando su ID.
     *
     * @param id Identificador único de la alerta (tipo Long).
     */
    public void eliminarAlerta(Long id) {
        if (!alertaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alerta no encontrada con ID: " + id); // <-- Verificación y excepción agregadas
        }
        alertaRepository.deleteById(id);
    }
}