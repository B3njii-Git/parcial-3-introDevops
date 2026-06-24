package com.bomberos.usuarios;



import com.bomberos.usuarios.model.Usuario;

import com.bomberos.usuarios.dto.UsuarioRequestDTO;

import com.bomberos.usuarios.dto.UsuarioResponseDTO;

import com.bomberos.usuarios.repository.UsuarioRepository;

import com.bomberos.usuarios.service.UsuarioService;
import com.bomberos.usuarios.security.JwtUtil;
import com.bomberos.usuarios.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;



import java.util.List;

import java.util.UUID;



import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)

public class UsuarioServiceTest {



    @Mock

    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks

    private UsuarioService usuarioService;



    @Test

    void obtenerUsuarios_RetornaDatos() {

        // Arrange

        Usuario usuario = new Usuario();

        usuario.setId(UUID.randomUUID()); // <-- CORRECCIÓN: Le asignamos un ID válido para evitar el NullPointerException



        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));



        // Act

        // Usamos obtenerUsuarios() tal como lo tienes definido en UsuarioService.java

        List<UsuarioResponseDTO> resultado = usuarioService.obtenerUsuarios();



        // Assert

        assertNotNull(resultado);

        assertEquals(1, resultado.size());

        verify(usuarioRepository).findAll();

    }



    @Test

    void crearUsuario_PersisteEnBaseDeDatos() {

        // Arrange

        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO();

        Usuario usuarioEntidad = new Usuario();

        usuarioEntidad.setId(UUID.randomUUID()); // <-- CORRECCIÓN: Le asignamos un ID al objeto simulado



        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEntidad);



        // Act

        var resultado = usuarioService.crearUsuario(requestDTO);



        // Assert

        assertNotNull(resultado);

        verify(usuarioRepository).save(any(Usuario.class));

    }



    @Test

    void eliminarUsuario_EjecutaBorradoPorId() {

        UUID id = UUID.randomUUID();

        when(usuarioRepository.existsById(id)).thenReturn(true);



        usuarioService.eliminarUsuario(String.valueOf(id));



        verify(usuarioRepository, times(1)).deleteById(id);

    }

    @Test
    void login_CredencialesCorrectas_RetornaAuth() {
        com.bomberos.usuarios.dto.LoginRequestDTO creds = new com.bomberos.usuarios.dto.LoginRequestDTO();
        creds.setEmail("test@mail.com");
        creds.setPassword("1234");
        
        Usuario usuario = new Usuario();
        usuario.setEmail("test@mail.com");
        usuario.setPassword("1234");
        usuario.setRol("ADMIN");
        
        when(usuarioRepository.findByEmail("test@mail.com")).thenReturn(java.util.Optional.of(usuario));
        when(jwtUtil.generarToken("test@mail.com", "ADMIN")).thenReturn("token123");
        
        var resultado = usuarioService.login(creds);
        
        assertNotNull(resultado);
        assertEquals("token123", resultado.getToken());
    }

    @Test
    void login_CredencialesIncorrectas_RetornaNull() {
        com.bomberos.usuarios.dto.LoginRequestDTO creds = new com.bomberos.usuarios.dto.LoginRequestDTO();
        creds.setEmail("test@mail.com");
        creds.setPassword("mala");
        
        Usuario usuario = new Usuario();
        usuario.setEmail("test@mail.com");
        usuario.setPassword("1234");
        
        when(usuarioRepository.findByEmail("test@mail.com")).thenReturn(java.util.Optional.of(usuario));
        
        var resultado = usuarioService.login(creds);
        
        assertNull(resultado);
    }

    @Test
    void crearUsuario_ConRol_PersisteEnBaseDeDatos() {
        com.bomberos.usuarios.dto.UsuarioRequestDTO requestDTO = new com.bomberos.usuarios.dto.UsuarioRequestDTO();
        requestDTO.setRol("BOMBERO");
        Usuario usuarioEntidad = new Usuario();
        usuarioEntidad.setId(UUID.randomUUID());

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEntidad);

        var resultado = usuarioService.crearUsuario(requestDTO);

        assertNotNull(resultado);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void eliminarUsuario_LanzaExcepcionCuandoNoExiste() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.eliminarUsuario(String.valueOf(id));
        });

        verify(usuarioRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void login_UsuarioNoEncontrado_RetornaNull() {
        com.bomberos.usuarios.dto.LoginRequestDTO creds = new com.bomberos.usuarios.dto.LoginRequestDTO();
        creds.setEmail("inexistente@mail.com");
        creds.setPassword("1234");
        
        when(usuarioRepository.findByEmail("inexistente@mail.com")).thenReturn(java.util.Optional.empty());
        
        var resultado = usuarioService.login(creds);
        
        assertNull(resultado);
    }
}