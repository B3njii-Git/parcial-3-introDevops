package com.bomberos.alertas;



import com.bomberos.alertas.model.Alerta;
import com.bomberos.alertas.dto.AlertaRequestDTO;
import com.bomberos.alertas.dto.AlertaResponseDTO;
import com.bomberos.alertas.repository.AlertaRepository;
import com.bomberos.alertas.service.AlertasService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import java.util.Arrays;
import java.util.List;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)

public class AlertasServiceTest {



    @Mock

    private AlertaRepository alertaRepository;



    @InjectMocks

    private AlertasService alertasService;



    @Test

    void obtenerAlertas_DeberiaRetornarListaDeAlertas() {

        // Arrange

        // Simulamos que la base de datos (el repositorio) devuelve Entidades

        when(alertaRepository.findAll()).thenReturn(Arrays.asList(new Alerta(), new Alerta()));



        // Act

        // El servicio procesa las Entidades y devuelve DTOs

        List<AlertaResponseDTO> resultado = alertasService.obtenerAlertas();



        // Assert

        assertNotNull(resultado);

        assertEquals(2, resultado.size());

        verify(alertaRepository, times(1)).findAll();

    }



    @Test

    void crearAlerta_DeberiaGuardarYRetornarAlerta() {

        // Arrange

        AlertaRequestDTO requestDTO = new AlertaRequestDTO();

        Alerta alertaEntidad = new Alerta();



        // Simulamos que al guardar cualquier Entidad en la BD, nos retorna esa misma Entidad

        when(alertaRepository.save(any(Alerta.class))).thenReturn(alertaEntidad);



        // Act

        // Llamamos al servicio pasando el DTO

        var resultado = alertasService.crearAlerta(requestDTO);



        // Assert

        assertNotNull(resultado);

        // Verificamos que el repositorio efectivamente guardó una Entidad

        verify(alertaRepository, times(1)).save(any(Alerta.class));

    }

    @Test
    void eliminarAlerta_DeberiaLlamarAlRepositorio() {
        Long id = 1L;
        when(alertaRepository.existsById(id)).thenReturn(true);
        doNothing().when(alertaRepository).deleteById(id);

        alertasService.eliminarAlerta(id);

        verify(alertaRepository, times(1)).deleteById(id);
    }

    @Test
    void eliminarAlerta_LanzaExcepcionCuandoNoExiste() {
        Long id = 1L;
        when(alertaRepository.existsById(id)).thenReturn(false);

        assertThrows(com.bomberos.alertas.exception.ResourceNotFoundException.class, () -> {
            alertasService.eliminarAlerta(id);
        });

        verify(alertaRepository, never()).deleteById(any(Long.class));
    }
}