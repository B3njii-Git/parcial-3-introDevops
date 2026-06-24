package com.bomberos.reportes;

import com.bomberos.reportes.model.Reporte;
import com.bomberos.reportes.dto.ReporteRequestDTO;
import com.bomberos.reportes.dto.ReporteResponseDTO;
import com.bomberos.reportes.repository.ReporteRepository;
import com.bomberos.reportes.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    void obtenerReportes_RetornaLista() {
        // Arrange
        Reporte reporte = new Reporte();
        reporte.setId(UUID.randomUUID()); // SOLUCIÓN: Le asignamos un ID para evitar el NullPointerException

        when(reporteRepository.findAll()).thenReturn(List.of(reporte));

        // Act
        List<ReporteResponseDTO> resultado = reporteService.obtenerTodos();

        // Assert
        assertEquals(1, resultado.size());
        verify(reporteRepository).findAll();
    }

    @Test
    void crearReporte_GuardaCorrectamente() {
        // Arrange
        ReporteRequestDTO requestDTO = new ReporteRequestDTO();
        requestDTO.setLatitud(10.0); // SOLUCIÓN: Agregamos latitud para evitar NullPointer
        requestDTO.setLongitud(20.0);

        Reporte reporteEntidad = new Reporte();
        reporteEntidad.setId(UUID.randomUUID()); // SOLUCIÓN: El objeto guardado necesita ID

        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteEntidad);

        // Act
        var resultado = reporteService.crearReporte(requestDTO);

        // Assert
        assertNotNull(resultado);
        verify(reporteRepository).save(any(Reporte.class));
    }

    @Test
    void eliminarReporte_LlamaMetodoDeleteDelRepositorio() {
        // Arrange
        UUID id = UUID.randomUUID();
        // SOLUCIÓN: Le decimos al Mock que el reporte SÍ existe
        when(reporteRepository.existsById(id)).thenReturn(true);

        // Act
        reporteService.eliminarReporte(id.toString());

        // Assert
        verify(reporteRepository, times(1)).deleteById(id);
    }

    // --- NUEVAS PRUEBAS PARA COBERTURA (Ramas faltantes y método Actualizar) ---

    @Test
    void eliminarReporte_NoExiste_LanzaExcepcion() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(reporteRepository.existsById(id)).thenReturn(false); // Simulamos que NO existe

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reporteService.eliminarReporte(id.toString());
        });
    }

    @Test
    void actualizarReporte_ReporteExiste_ActualizaCorrectamente() {
        // Arrange
        UUID id = UUID.randomUUID();
        ReporteRequestDTO req = new ReporteRequestDTO();
        req.setLatitud(15.0);
        req.setLongitud(-30.0);

        Reporte existente = new Reporte();
        existente.setId(id);

        when(reporteRepository.findById(id)).thenReturn(Optional.of(existente));
        when(reporteRepository.save(any(Reporte.class))).thenReturn(existente);

        // Act
        ReporteResponseDTO res = reporteService.actualizarReporte(id.toString(), req);

        // Assert
        assertNotNull(res);
        verify(reporteRepository).save(existente);
    }

    @Test
    void actualizarReporte_NoExiste_LanzaExcepcion() {
        // Arrange
        UUID id = UUID.randomUUID();
        ReporteRequestDTO req = new ReporteRequestDTO();

        when(reporteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reporteService.actualizarReporte(id.toString(), req);
        });
    }
}