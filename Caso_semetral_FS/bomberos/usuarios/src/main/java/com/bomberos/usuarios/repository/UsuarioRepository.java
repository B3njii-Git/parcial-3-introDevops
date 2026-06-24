package com.bomberos.usuarios.repository;

import com.bomberos.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    // Spring Data JPA crea la query automáticamente con esta firma
    Optional<Usuario> findByEmail(String email);
}