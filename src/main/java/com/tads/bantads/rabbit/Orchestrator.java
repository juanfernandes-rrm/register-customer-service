package com.tads.bantads.rabbit;

import com.tads.bantads.dto.CustomerDTO;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Orchestrator {

    private static final String CUSTOMER_CREATED_QUEUE = "customer-created-queue";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendCustomerCreatedToQueue(CustomerDTO customerDTO) {
        try {
            rabbitTemplate.convertAndSend(CUSTOMER_CREATED_QUEUE, customerDTO);
            System.out.println("Mensagem enviada para a fila: " + customerDTO);
        } catch (AmqpException e) {
            System.out.println("Falha ao enviar mensagem para a fila: " + e.getMessage());
        }
    }
}