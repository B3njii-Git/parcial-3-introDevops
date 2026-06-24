package com.bomberos.usuarios.controller;

import com.bomberos.usuarios.dto.AuthResponseDTO; //Importación para manejar el Token
import com.bomberos.usuarios.dto.LoginRequestDTO;
import com.bomberos.usuarios.dto.UsuarioRequestDTO;
import com.bomberos.usuarios.dto.UsuarioResponseDTO;
import com.bomberos.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que gestiona las operaciones de los usuarios del sistema.
 * Expone los endpoints para la creación, consulta, eliminación y autenticación.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene la lista completa de usuarios registrados en el sistema.
     *
     * @return Una lista de objetos UsuarioResponseDTO con los datos filtrados de los usuarios.
     */
    @GetMapping
    public List<UsuarioResponseDTO> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    /**
     * Crea un nuevo usuario en la base de datos municipal.
     *
     * @param requestDTO Objeto con los datos de registro (nombre, email, password, rol).
     * @return Objeto UsuarioResponseDTO con los datos del usuario recién creado.
     */
    @PostMapping
    public UsuarioResponseDTO crearUsuario(@RequestBody UsuarioRequestDTO requestDTO) {
        return usuarioService.crearUsuario(requestDTO);
    }

    /**
     * Elimina un usuario del sistema mediante su identificador único.
     *
     * @param id El identificador UUID del usuario a eliminar, en formato String.
     * @return ResponseEntity sin contenido indicando el éxito de la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Autentica a un usuario en el sistema y le entrega su credencial digital (Token).
     *
     * @param credenciales Objeto con el email y contraseña del usuario.
     * @return ResponseEntity con AuthResponseDTO si es exitoso, o código 401 si falla.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginRequestDTO credenciales) {
        // AUTENTICACIÓN: Recibe la credencial digital generada por el servicio
        AuthResponseDTO response = usuarioService.login(credenciales);

        if (response != null) {
            // Retorna el objeto AuthResponseDTO con el Token en formato JSON
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
}