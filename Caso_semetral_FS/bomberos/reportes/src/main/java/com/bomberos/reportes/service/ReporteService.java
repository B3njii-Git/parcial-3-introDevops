package com.bomberos.reportes.service;

import com.bomberos.reportes.dto.ReporteRequestDTO;
import com.bomberos.reportes.dto.ReporteResponseDTO;
import com.bomberos.reportes.exception.ResourceNotFoundException; // <-- Import de la excepción agregado
import com.bomberos.reportes.model.Reporte;
import com.bomberos.reportes.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio de lógica de negocio para la entidad Reporte.
 * Encargado de aplicar las reglas de validación de incidentes, el mapeo de datos entre
 * entidades y DTOs, y la persistencia final a través del repositorio.
 */
@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    /**
     * Convierte una entidad interna Reporte a su correspondiente DTO de salida.
     * Abstrae la estructura de la base de datos protegiendo el modelo interno del sistema.
     *
     * @param reporte La entidad original proveniente de la base de datos PostgreSQL.
     * @return Un objeto ReporteResponseDTO formateado para su envío al cliente.
     */
    private ReporteResponseDTO mapToDTO(Reporte reporte) {
        ReporteResponseDTO dto = new ReporteResponseDTO();
        dto.setId(reporte.getId().toString());
        dto.setDescripcion(reporte.getDescripcion());
        dto.setLatitud(reporte.getLatitud());
        dto.setLongitud(reporte.getLongitud());
        dto.setEstado(reporte.getEstado());
        return dto;
    }

    /**
     * Consulta la base de datos para extraer todos los reportes y los transforma en DTOs.
     *
     * @return Lista de objetos ReporteResponseDTO ordenados o filtrados.
     */
    // 1. Obtener todos (Retorna DTOs)
    public List<ReporteResponseDTO> obtenerTodos() {
        return reporteRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recibe los datos de un nuevo incidente, inicializa sus propiedades de auditoría
     * y gestiona su almacenamiento definitivo en el sistema.
     *
     * @param request Objeto de transferencia de datos con la información enviada por el usuario.
     * @return El ReporteResponseDTO resultante de la persistencia exitosa.
     */
    // 2. Crear (Recibe RequestDTO, Retorna ResponseDTO)
    public ReporteResponseDTO crearReporte(ReporteRequestDTO request) {
        Reporte reporte = new Reporte();
        reporte.setDescripcion(request.getDescripcion());
        reporte.setLatitud(request.getLatitud());
        reporte.setLongitud(request.getLongitud());
        reporte.setEstado(request.getEstado());

        Reporte guardado = reporteRepository.save(reporte);
        return mapToDTO(guardado);
    }

    // 3. Actualizar (Recibe DTO, Retorna DTO)
    public ReporteResponseDTO actualizarReporte(String id, ReporteRequestDTO request) {
        Reporte reporteExistente = reporteRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con ID: " + id)); // <-- Excepción personalizada aquí

        reporteExistente.setDescripcion(request.getDescripcion());
        reporteExistente.setLatitud(request.getLatitud());
        reporteExistente.setLongitud(request.getLongitud());
        reporteExistente.setEstado(request.getEstado());

        Reporte guardado = reporteRepository.save(reporteExistente);
        return mapToDTO(guardado);
    }

    // 4. Eliminar
    public void eliminarReporte(String id) {
        UUID uuid = UUID.fromString(id);
        if (!reporteRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Reporte no encontrado con ID: " + id); // <-- Excepción personalizada aquí
        }
        reporteRepository.deleteById(uuid);
    }
}