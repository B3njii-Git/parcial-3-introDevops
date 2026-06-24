package com.bomberos.alertas;

import com.bomberos.alertas.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void enviarCorreo_Exitoso() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        
        emailService.enviarCorreo("test@mail.com", "Asunto", "Cuerpo");
        
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void enviarCorreo_Falla() {
        doThrow(new RuntimeException("Error simulado")).when(mailSender).send(any(SimpleMailMessage.class));
        
        emailService.enviarCorreo("test@mail.com", "Asunto", "Cuerpo");
        
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
