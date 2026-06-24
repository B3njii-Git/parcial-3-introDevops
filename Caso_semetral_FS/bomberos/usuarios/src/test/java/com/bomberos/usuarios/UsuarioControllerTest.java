package com.bomberos.usuarios;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bomberos.usuarios.controller.UsuarioController;
import com.bomberos.usuarios.dto.UsuarioRequestDTO;
import com.bomberos.usuarios.dto.UsuarioResponseDTO;
import com.bomberos.usuarios.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void testObtenerTodosLosUsuarios() {
        // Arrange
        UsuarioResponseDTO user1 = new UsuarioResponseDTO();
        user1.setId("uuid-1");
        user1.setNombre("Juan");

        UsuarioResponseDTO user2 = new UsuarioResponseDTO();
        user2.setId("uuid-2");
        user2.setNombre("Pedro");

        when(usuarioService.obtenerUsuarios()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<UsuarioResponseDTO> resultado = usuarioController.obtenerTodosLosUsuarios();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        verify(usuarioService, times(1)).obtenerUsuarios();
    }

    @Test
    void testCrearUsuario() {
        // Arrange
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setNombre("Maria");
        request.setEmail("maria@mail.com");

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId("uuid-3");
        response.setNombre("Maria");
        response.setEmail("maria@mail.com");

        when(usuarioService.crearUsuario(any(UsuarioRequestDTO.class))).thenReturn(response);

        // Act
        UsuarioResponseDTO resultado = usuarioController.crearUsuario(request);

        // Assert
        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNombre());
        verify(usuarioService, times(1)).crearUsuario(any(UsuarioRequestDTO.class));
    }

    @Test
    void testEliminarUsuario() {
        String id = "uuid-test";
        doNothing().when(usuarioService).eliminarUsuario(id);

        var resultado = usuarioController.eliminarUsuario(id);

        assertEquals(204, resultado.getStatusCode().value());
        verify(usuarioService, times(1)).eliminarUsuario(id);
    }

    @Test
    void testLoginUsuario_Exitoso() {
        com.bomberos.usuarios.dto.LoginRequestDTO credenciales = new com.bomberos.usuarios.dto.LoginRequestDTO();
        credenciales.setEmail("test@mail.com");
        credenciales.setPassword("1234");
        
        com.bomberos.usuarios.dto.AuthResponseDTO authResponse = new com.bomberos.usuarios.dto.AuthResponseDTO("test@mail.com", "ADMIN", "token123");
        when(usuarioService.login(any(com.bomberos.usuarios.dto.LoginRequestDTO.class))).thenReturn(authResponse);

        var resultado = usuarioController.loginUsuario(credenciales);

        assertEquals(200, resultado.getStatusCode().value());
        assertNotNull(resultado.getBody());
    }

    @Test
    void testLoginUsuario_Fallido() {
        com.bomberos.usuarios.dto.LoginRequestDTO credenciales = new com.bomberos.usuarios.dto.LoginRequestDTO();
        credenciales.setEmail("test@mail.com");
        credenciales.setPassword("mala");
        
        when(usuarioService.login(any(com.bomberos.usuarios.dto.LoginRequestDTO.class))).thenReturn(null);

        var resultado = usuarioController.loginUsuario(credenciales);

        assertEquals(401, resultado.getStatusCode().value());
    }
}