package com.piedraazul.appointment_service.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PATIENT_QUEUE = "patient.registered";
    public static final String PROFESSIONAL_QUEUE = "professional.registered";

    @Bean
    public Queue patientQueue() {
        return new Queue(PATIENT_QUEUE, true);
    }

    @Bean
    public Queue professionalQueue() {
        return new Queue(PROFESSIONAL_QUEUE, true);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new JacksonJsonMessageConverter());
        return template;
    }
}