package com.bomberos.historial.messaging;

import com.bomberos.historial.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class HistorialConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void recibirMensaje(String mensaje) {
        System.out.println("----------------------------------------");
        System.out.println("[HISTORIAL] Mensaje recibido de RabbitMQ:");
        System.out.println("Registrando en historial el evento: " + mensaje);
        System.out.println("----------------------------------------");
    }
}
