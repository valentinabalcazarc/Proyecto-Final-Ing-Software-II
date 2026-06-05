package com.piedraazul.appointment_service.infrastructure.adapter.in.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PATIENT_QUEUE = "patient.registered";
    public static final String PATIENT_UPDATED_QUEUE = "patient.updated";
    public static final String PROFESSIONAL_QUEUE = "professional.registered";
    public static final String PROFESSIONAL_UPDATED_QUEUE = "professional.updated";
    public static final String UNAVAILABLE_DAY_QUEUE = "unavailableday.created";
    public static final String UNAVAILABLE_DAY_DELETED_QUEUE = "unavailableday.deleted";

    @Bean
    public Queue patientQueue() {
        return new Queue(PATIENT_QUEUE, true);
    }

    @Bean
    public Queue patientUpdatedQueue() {
        return new Queue(PATIENT_UPDATED_QUEUE, true);
    }

    @Bean
    public Queue professionalQueue() {
        return new Queue(PROFESSIONAL_QUEUE, true);
    }

    @Bean
    public Queue professionalUpdatedQueue() {
        return new Queue(PROFESSIONAL_UPDATED_QUEUE, true);
    }

    @Bean
    public Queue unavailableDayQueue() {
        return new Queue(UNAVAILABLE_DAY_QUEUE, true);
    }

    @Bean
    public Queue unavailableDayDeletedQueue() {
        return new Queue(UNAVAILABLE_DAY_DELETED_QUEUE, true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(mapper);
        converter.setAlwaysConvertToInferredType(true);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}
