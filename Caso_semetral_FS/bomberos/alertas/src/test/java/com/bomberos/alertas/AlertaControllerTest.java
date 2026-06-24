package com.bomberos.alertas;

import com.bomberos.alertas.controller.AlertaController;
import com.bomberos.alertas.dto.AlertaRequestDTO;
import com.bomberos.alertas.dto.AlertaResponseDTO;
import com.bomberos.alertas.service.AlertasService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlertaControllerTest {

    @Mock
    private AlertasService alertasService;

    @InjectMocks
    private AlertaController alertaController;

    @Test
    public void testObtenerAlertas() {
        // 1. Arrange: Preparamos los datos simulados usando DTOs
        AlertaResponseDTO dto = new AlertaResponseDTO();
        dto.setId(1L);
        dto.setTipoAlerta("INCENDIO");
        dto.setMensaje("Fuego estructural detectado");
        dto.setSeveridad("ALTA");
        dto.setFechaCreacion(LocalDateTime.now());

        when(alertasService.obtenerAlertas()).thenReturn(Arrays.asList(dto));

        // 2. Act: Llamamos al método del controlador
        List<AlertaResponseDTO> respuesta = alertaController.obtenerAlertas();

        // 3. Assert: Verificamos que responda correctamente
        assertNotNull(respuesta);
        assertEquals(1, respuesta.size());
        assertEquals("INCENDIO", respuesta.get(0).getTipoAlerta());
        assertEquals("ALTA", respuesta.get(0).getSeveridad());
    }

    @Test
    public void testCrearAlerta() {
        // 1. Arrange: Preparamos el RequestDTO que "enviaría el usuario"
        AlertaRequestDTO request = new AlertaRequestDTO();
        request.setTipoAlerta("CLIMA");
        request.setMensaje("Alerta de vientos fuertes");
        request.setSeveridad("MEDIA");

        // Preparamos el ResponseDTO que devolvería el servicio
        AlertaResponseDTO response = new AlertaResponseDTO();
        response.setId(2L);
        response.setTipoAlerta("CLIMA");
        response.setMensaje("Alerta de vientos fuertes");
        response.setSeveridad("MEDIA");
        response.setFechaCreacion(LocalDateTime.now());

        when(alertasService.crearAlerta(any(AlertaRequestDTO.class))).thenReturn(response);

        // 2. Act
        AlertaResponseDTO resultado = alertaController.crearAlerta(request);

        // 3. Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("CLIMA", resultado.getTipoAlerta());
    }

    @Test
    public void testEliminarAlerta() {
        Long id = 1L;
        org.mockito.Mockito.doNothing().when(alertasService).eliminarAlerta(id);

        alertaController.eliminarAlerta(id);

        org.mockito.Mockito.verify(alertasService, org.mockito.Mockito.times(1)).eliminarAlerta(id);
    }
}