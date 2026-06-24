package com.bomberos.usuarios.service;

import com.bomberos.usuarios.dto.AuthResponseDTO; // <-- Agregado para enviar el Token
import com.bomberos.usuarios.dto.LoginRequestDTO;
import com.bomberos.usuarios.dto.UsuarioRequestDTO;
import com.bomberos.usuarios.dto.UsuarioResponseDTO;
import com.bomberos.usuarios.exception.ResourceNotFoundException; // <-- Import de la excepción agregado
import com.bomberos.usuarios.model.Usuario;
import com.bomberos.usuarios.repository.UsuarioRepository;
import com.bomberos.usuarios.security.JwtUtil; // <-- Agregado para usar el generador de Token
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la lógica de negocio para la entidad Usuario.
 * Gestiona el mapeo de DTOs, validaciones, seguridad y comunicación con el repositorio.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil; // <-- Inyectamos el componente JWT

    /**
     * Convierte una entidad Usuario en su representación DTO de respuesta.
     * Filtra datos sensibles como la contraseña.
     *
     * @param usuario La entidad Usuario obtenida de la base de datos.
     * @return Objeto UsuarioResponseDTO con los datos permitidos para el cliente.
     */
    private UsuarioResponseDTO mapToDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId().toString()); // Convertimos UUID a String
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setRol(usuario.getRol());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        return dto;
    }

    /**
     * Recupera todos los usuarios activos en el sistema y los convierte a DTO.
     *
     * @return Lista de UsuarioResponseDTO.
     */
    @Cacheable(value = "usuarios")
    public List<UsuarioResponseDTO> obtenerUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Guarda un nuevo usuario aplicando las reglas de negocio correspondientes.
     *
     * @param request Objeto que contiene los datos ingresados en el formulario.
     * @return UsuarioResponseDTO con la información del usuario persistido.
     */
    @CacheEvict(value = "usuarios", allEntries = true)
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setPassword(request.getPassword());
        if (request.getRol() != null) {
            usuario.setRol(request.getRol());
        }

        Usuario guardado = usuarioRepository.save(usuario);
        return mapToDTO(guardado);
    }

    /**
     * Elimina un usuario de la base de datos de manera definitiva.
     *
     * @param id Identificador único del usuario (UUID en formato String).
     */
    @CacheEvict(value = "usuarios", allEntries = true)
    public void eliminarUsuario(String id) {
        UUID uuid = UUID.fromString(id);
        if (!usuarioRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id); // <-- Verificación y excepción agregadas
        }
        usuarioRepository.deleteById(uuid);
    }


    /**
     * Valida las credenciales ingresadas y genera el token de acceso JWT si son correctas.
     *
     * @param credenciales Objeto con el email y la contraseña.
     * @return Objeto AuthResponseDTO con el token generado, o null si las credenciales son incorrectas.
     */
    public AuthResponseDTO login(LoginRequestDTO credenciales) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(credenciales.getEmail());

        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(credenciales.getPassword())) {
            Usuario usuario = usuarioOpt.get();

            // AUTENTICACIÓN EXITOSA: Ya sabemos quién es.
            // Generamos la credencial digital (Token) que incluye su rol (Autorización)
            String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());

            // Retornamos el DTO que incluye el email, el rol y el Token
            return new AuthResponseDTO(usuario.getEmail(), usuario.getRol(), token);
        }
        return null; // Retorna nulo si fallan las credenciales
    }
}