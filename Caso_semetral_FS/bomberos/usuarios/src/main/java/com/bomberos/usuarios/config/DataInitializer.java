package com.bomberos.usuarios.config;

import com.bomberos.usuarios.model.Usuario;
import com.bomberos.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin Valle del Sol");
            admin.setEmail("usuario@valledelsol.cl");
            admin.setPassword("12345678"); // Contraseña por defecto
            admin.setRol("BRIGADISTA");
            admin.setTelefono("+56912345678");
            
            usuarioRepository.save(admin);
            System.out.println("Usuario de prueba creado exitosamente: usuario@valledelsol.cl / 12345678");
        }
    }
}
