package com.bomberos.historial;



import com.bomberos.historial.model.Historial;

import com.bomberos.historial.dto.HistorialRequestDTO;

import com.bomberos.historial.dto.HistorialResponseDTO;

import com.bomberos.historial.repository.HistorialRepository;

import com.bomberos.historial.service.HistorialService;

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

public class HistorialServiceTest {



    @Mock

    private HistorialRepository historialRepository;



    @InjectMocks

    private HistorialService historialService;



    @Test

    void listar_DeberiaRetornarHistoriales() {

        // Arrange

        when(historialRepository.findAll()).thenReturn(Arrays.asList(new Historial()));



        // Act

        List<HistorialResponseDTO> resultado = historialService.listar();



        // Assert

        assertNotNull(resultado);

        assertEquals(1, resultado.size());

        verify(historialRepository, times(1)).findAll();

    }



    @Test

    void guardar_DeberiaGuardarExitosamente() {

        // Arrange

        HistorialRequestDTO requestDTO = new HistorialRequestDTO();

        Historial historialEntidad = new Historial();

        when(historialRepository.save(any(Historial.class))).thenReturn(historialEntidad);



        // Act

        var resultado = historialService.guardar(requestDTO);



        // Assert

        assertNotNull(resultado);

        verify(historialRepository, times(1)).save(any(Historial.class));

    }

}