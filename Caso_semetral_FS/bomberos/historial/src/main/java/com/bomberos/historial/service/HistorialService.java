package com.bomberos.historial.service;

import com.bomberos.historial.dto.HistorialRequestDTO;
import com.bomberos.historial.dto.HistorialResponseDTO;
import com.bomberos.historial.model.Historial;
import com.bomberos.historial.repository.HistorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la lógica de negocio para los registros históricos.
 * Administra la transformación de datos (Entidad a DTO) y la comunicación con la
 * base de datos para almacenar la bitácora de operaciones de las brigadas.
 */
@Service
public class HistorialService {

    @Autowired
    private HistorialRepository repository;

    /**
     * Convierte una entidad Historial extraída de la base de datos a su formato DTO correspondiente.
     * Este proceso asegura que solo los datos pertinentes viajen hacia el Frontend.
     *
     * @param historial La entidad Historial original.
     * @return Un objeto HistorialResponseDTO estructurado para la vista.
     */
    private HistorialResponseDTO mapToDTO(Historial historial) {
        HistorialResponseDTO dto = new HistorialResponseDTO();
        dto.setId(historial.getId());
        dto.setUbicación(historial.getUbicación());
        dto.setCausaProbable(historial.getCausaProbable());
        dto.setFechaInicio(historial.getFechaInicio());
        dto.setFechaFin(historial.getFechaFin());
        dto.setHectareasAfectadas(historial.getHectareasAfectadas());
        return dto;
    }

    /**
     * Extrae todos los registros históricos almacenados en el repositorio y los mapea a una lista DTO.
     *
     * @return Lista de objetos HistorialResponseDTO.
     */
    public List<HistorialResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Procesa la solicitud de un nuevo registro histórico, instanciando la entidad y guardándola
     * definitivamente en la base de datos.
     *
     * @param request Objeto que transfiere los datos del evento a registrar.
     * @return El HistorialResponseDTO resultante con los datos guardados.
     */
    public HistorialResponseDTO guardar(HistorialRequestDTO request) {
        Historial historial = new Historial();
        historial.setUbicación(request.getUbicación());
        historial.setCausaProbable(request.getCausaProbable());
        historial.setFechaInicio(request.getFechaInicio());
        historial.setFechaFin(request.getFechaFin());
        historial.setHectareasAfectadas(request.getHectareasAfectadas());

        Historial guardado = repository.save(historial);
        return mapToDTO(guardado);
    }
}