package com.bomberos.historial;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bomberos.historial.controller.HistorialController;
import com.bomberos.historial.dto.HistorialRequestDTO;
import com.bomberos.historial.dto.HistorialResponseDTO;
import com.bomberos.historial.service.HistorialService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class HistorialControllerTest {

    // Mockeamos el Service, no el Repository, ya que el Controller habla con el Service
    @Mock
    private HistorialService historialService;

    @InjectMocks
    private HistorialController historialController;

    @Test
    void testObtenerHistoriales() {
        // Arrange
        HistorialResponseDTO h1 = new HistorialResponseDTO();
        h1.setId(1L);
        h1.setUbicación("Sector Norte");

        HistorialResponseDTO h2 = new HistorialResponseDTO();
        h2.setId(2L);
        h2.setUbicación("Sector Sur");

        when(historialService.listar()).thenReturn(Arrays.asList(h1, h2));

        // Act
        List<HistorialResponseDTO> resultado = historialController.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(historialService, times(1)).listar();
    }

    @Test
    void testGuardarHistorial() {
        // Arrange
        HistorialRequestDTO request = new HistorialRequestDTO();
        request.setUbicación("Valle Verde");
        request.setCausaProbable("Natural");
        request.setHectareasAfectadas(15.5);

        HistorialResponseDTO response = new HistorialResponseDTO();
        response.setId(3L);
        response.setUbicación("Valle Verde");
        response.setCausaProbable("Natural");
        response.setHectareasAfectadas(15.5);

        when(historialService.guardar(any(HistorialRequestDTO.class))).thenReturn(response);

        // Act
        HistorialResponseDTO resultado = historialController.guardar(request);

        // Assert
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Valle Verde", resultado.getUbicación());
        verify(historialService, times(1)).guardar(any(HistorialRequestDTO.class));
    }
}