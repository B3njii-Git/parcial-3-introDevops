package com.bomberos.monitoreo;



import com.bomberos.monitoreo.model.ZonaMonitoreo;

import com.bomberos.monitoreo.dto.ZonaMonitoreoRequestDTO;

import com.bomberos.monitoreo.dto.ZonaMonitoreoResponseDTO;

import com.bomberos.monitoreo.repository.MonitoreoRepository;

import com.bomberos.monitoreo.service.MonitoreoService;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;



import java.util.Collections;

import java.util.List;



import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)

public class MonitoreoServiceTest {



    @Mock

    private MonitoreoRepository monitoreoRepository;



    @InjectMocks

    private MonitoreoService monitoreoService;



    @Test

    void listarTodo_CuandoNoHayDatos_RetornaListaVacia() {

        // Arrange

        when(monitoreoRepository.findAll()).thenReturn(Collections.emptyList());



        // Act

        List<ZonaMonitoreoResponseDTO> resultado = monitoreoService.listarTodo();



        // Assert

        assertTrue(resultado.isEmpty());

        verify(monitoreoRepository, times(1)).findAll();

    }



    @Test

    void guardar_DeberiaPersistirZona() {

        // Arrange

        ZonaMonitoreoRequestDTO requestDTO = new ZonaMonitoreoRequestDTO();

        ZonaMonitoreo zonaEntidad = new ZonaMonitoreo();

        when(monitoreoRepository.save(any(ZonaMonitoreo.class))).thenReturn(zonaEntidad);



        // Act

        var resultado = monitoreoService.guardar(requestDTO);



        // Assert

        assertNotNull(resultado);

        verify(monitoreoRepository, times(1)).save(any(ZonaMonitoreo.class));

    }

}