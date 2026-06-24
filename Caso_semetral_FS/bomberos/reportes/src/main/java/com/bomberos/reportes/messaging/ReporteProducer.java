package com.bomberos.reportes.messaging;

import com.bomberos.reportes.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarReporteCreado(String mensaje) {
        System.out.println("Enviando mensaje a RabbitMQ: " + mensaje);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "reporte.creado", mensaje);
    }
}
