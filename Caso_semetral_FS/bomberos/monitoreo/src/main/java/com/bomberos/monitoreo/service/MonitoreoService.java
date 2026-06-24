package com.bomberos.monitoreo.service;

import com.bomberos.monitoreo.dto.ZonaMonitoreoRequestDTO;
import com.bomberos.monitoreo.dto.ZonaMonitoreoResponseDTO;
import com.bomberos.monitoreo.model.ZonaMonitoreo;
import com.bomberos.monitoreo.repository.MonitoreoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que centraliza la lógica de negocio para las zonas de monitoreo.
 * Se encarga de la persistencia de datos espaciales (coordenadas), la asignación de niveles de riesgo
 * y el mapeo de entidades hacia objetos de transferencia (DTOs).
 */
@Service
public class MonitoreoService {

    @Autowired
    private MonitoreoRepository monitoreoRepository;

    /**
     * Transforma una entidad ZonaMonitoreo nativa de la base de datos en un DTO de respuesta.
     * Aisla la estructura interna de la base de datos del cliente web.
     *
     * @param zona Entidad ZonaMonitoreo recuperada de la base de datos.
     * @return Objeto ZonaMonitoreoResponseDTO con los datos filtrados y listos para ser enviados al cliente.
     */
    private ZonaMonitoreoResponseDTO mapToDTO(ZonaMonitoreo zona) {
        ZonaMonitoreoResponseDTO dto = new ZonaMonitoreoResponseDTO();
        dto.setId(zona.getId());
        dto.setNombreZona(zona.getNombreZona());
        dto.setLatitud(zona.getLatitud());
        dto.setLongitud(zona.getLongitud());
        dto.setNivelRiesgo(zona.getNivelRiesgo());
        dto.setBrigadaActiva(zona.getBrigadaActiva());
        return dto;
    }

    /**
     * Consulta el repositorio para extraer todas las zonas de riesgo vigentes y las mapea a formato DTO.
     *
     * @return Lista de objetos ZonaMonitoreoResponseDTO.
     */
    public List<ZonaMonitoreoResponseDTO> listarTodo() {
        return monitoreoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva entidad ZonaMonitoreo a partir de los datos recibidos,
     * procesa sus atributos y la persiste definitivamente en la base de datos.
     *
     * @param request Objeto que contiene los datos geográficos y operacionales de la nueva zona.
     * @return Objeto ZonaMonitoreoResponseDTO con la información de la zona persistida.
     */
    public ZonaMonitoreoResponseDTO guardar(ZonaMonitoreoRequestDTO request) {
        ZonaMonitoreo zona = new ZonaMonitoreo();
        zona.setNombreZona(request.getNombreZona());
        zona.setLatitud(request.getLatitud());
        zona.setLongitud(request.getLongitud());
        zona.setNivelRiesgo(request.getNivelRiesgo());
        zona.setBrigadaActiva(request.getBrigadaActiva());

        ZonaMonitoreo guardada = monitoreoRepository.save(zona);
        return mapToDTO(guardada);
    }
}