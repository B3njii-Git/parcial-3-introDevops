package com.bomberos.monitoreo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bomberos.monitoreo.controller.MonitoreoController;
import com.bomberos.monitoreo.dto.ZonaMonitoreoRequestDTO;
import com.bomberos.monitoreo.dto.ZonaMonitoreoResponseDTO;
import com.bomberos.monitoreo.service.MonitoreoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MonitoreoControllerTest {

    // Mockeamos el Service, no el Repository
    @Mock
    private MonitoreoService monitoreoService;

    @InjectMocks
    private MonitoreoController monitoreoController;

    @Test
    void testObtenerZonas() {
        // Arrange
        ZonaMonitoreoResponseDTO zona1 = new ZonaMonitoreoResponseDTO();
        zona1.setId(1L);
        zona1.setNombreZona("Bosque Sur");

        ZonaMonitoreoResponseDTO zona2 = new ZonaMonitoreoResponseDTO();
        zona2.setId(2L);
        zona2.setNombreZona("Cerro Alto");

        when(monitoreoService.listarTodo()).thenReturn(Arrays.asList(zona1, zona2));

        // Act
        List<ZonaMonitoreoResponseDTO> resultado = monitoreoController.listarTodo();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(monitoreoService, times(1)).listarTodo();
    }

    @Test
    void testGuardarZona() {
        // Arrange
        ZonaMonitoreoRequestDTO request = new ZonaMonitoreoRequestDTO();
        request.setNombreZona("Valle Central");
        request.setLatitud(-41.4);
        request.setLongitud(-72.9);
        request.setNivelRiesgo("ALTO");
        request.setBrigadaActiva(true);

        ZonaMonitoreoResponseDTO response = new ZonaMonitoreoResponseDTO();
        response.setId(3L);
        response.setNombreZona("Valle Central");
        response.setNivelRiesgo("ALTO");

        when(monitoreoService.guardar(any(ZonaMonitoreoRequestDTO.class))).thenReturn(response);

        // Act
        ZonaMonitoreoResponseDTO resultado = monitoreoController.guardar(request);

        // Assert
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Valle Central", resultado.getNombreZona());
        verify(monitoreoService, times(1)).guardar(any(ZonaMonitoreoRequestDTO.class));
    }
}