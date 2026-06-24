package com.bomberos.reportes;

import com.bomberos.reportes.controller.ReporteController;
import com.bomberos.reportes.dto.ReporteRequestDTO;
import com.bomberos.reportes.dto.ReporteResponseDTO;
import com.bomberos.reportes.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReporteControllerTest {

    @Mock
    private ReporteService reporteService;

    @Mock
    private com.bomberos.reportes.messaging.ReporteProducer reporteProducer;

    @InjectMocks
    private ReporteController reporteController;

    @Test
    public void testListarReportes() {
        ReporteResponseDTO reporteDto = new ReporteResponseDTO();
        reporteDto.setDescripcion("Incendio en prueba unitaria");

        when(reporteService.obtenerTodos()).thenReturn(Arrays.asList(reporteDto));

        ResponseEntity<List<ReporteResponseDTO>> respuesta = reporteController.listarReportes();

        assertNotNull(respuesta.getBody());
        assertEquals(200, respuesta.getStatusCode().value());
        assertEquals(1, respuesta.getBody().size());
        verify(reporteService, times(1)).obtenerTodos();
    }

    @Test
    public void testCrearReporte() {
        // Arrange
        ReporteRequestDTO request = new ReporteRequestDTO();
        request.setDescripcion("Nuevo Reporte");

        ReporteResponseDTO response = new ReporteResponseDTO();
        response.setDescripcion("Nuevo Reporte");

        when(reporteService.crearReporte(any(ReporteRequestDTO.class))).thenReturn(response);

        // Act
        ResponseEntity<ReporteResponseDTO> respuesta = reporteController.crearReporte(request);

        // Assert
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Nuevo Reporte", respuesta.getBody().getDescripcion());
    }

    @Test
    public void testEliminarReporte() {
        // Arrange
        String id = "uuid-123";

        // Act
        ResponseEntity<Void> respuesta = reporteController.eliminarReporte(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(reporteService, times(1)).eliminarReporte(id);
    }

    @Test
    public void testActualizarReporte() {
        // Arrange
        String id = "uuid-123";
        ReporteRequestDTO request = new ReporteRequestDTO();
        request.setDescripcion("Reporte Actualizado");

        ReporteResponseDTO response = new ReporteResponseDTO();
        response.setDescripcion("Reporte Actualizado");

        when(reporteService.actualizarReporte(eq(id), any(ReporteRequestDTO.class))).thenReturn(response);

        // Act
        ResponseEntity<ReporteResponseDTO> respuesta = reporteController.actualizarReporte(id, request);

        // Assert
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Reporte Actualizado", respuesta.getBody().getDescripcion());
    }
}