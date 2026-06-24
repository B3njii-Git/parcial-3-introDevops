package com.bomberos.alertas.messaging;

import com.bomberos.alertas.config.RabbitMQConfig;
import com.bomberos.alertas.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertaConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void recibirMensaje(String mensaje) {
        System.out.println("----------------------------------------");
        System.out.println("[ALERTA] Mensaje recibido de RabbitMQ:");
        System.out.println("Enviando notificación por correo para el evento...");
        
        // Aquí puedes cambiar el correo de destino por el que necesites
        String destinatario = "admin.bomberos@yopmail.com"; 
        String asunto = "¡Nueva Alerta de Emergencia!";
        
        emailService.enviarCorreo(destinatario, asunto, mensaje);
        
        System.out.println("----------------------------------------");
    }
}
