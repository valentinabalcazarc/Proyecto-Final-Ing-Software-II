package com.piedraazul.people_service.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_QUEUE = "user.registered";
    public static final String USER_UPDATED_QUEUE = "user.updated";
    public static final String PATIENT_QUEUE = "patient.registered";
    public static final String PATIENT_UPDATED_QUEUE = "patient.updated";
    public static final String PROFESSIONAL_UPDATED_QUEUE = "professional.updated";
    public static final String PROFESSIONAL_QUEUE = "professional.registered";
    public static final String UNAVAILABLE_DAY_QUEUE = "unavailableday.created";
    public static final String UNAVAILABLE_DAY_DELETED_QUEUE = "unavailableday.deleted";

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE, true);
    }

    @Bean
    public Queue userUpdatedQueue() {
        return new Queue(USER_UPDATED_QUEUE, true);
    }

    @Bean
    public Queue patientQueue() {
        return new Queue(PATIENT_QUEUE, true);
    }

    @Bean
    public Queue patientUpdatedQueue() {
        return new Queue(PATIENT_UPDATED_QUEUE, true);
    }

    @Bean
    public Queue professionalUpdatedQueue() {
        return new Queue(PROFESSIONAL_UPDATED_QUEUE, true);
    }

    @Bean
    public Queue professionalQueue() {
        return new Queue(PROFESSIONAL_QUEUE, true);
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
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new JacksonJsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new JacksonJsonMessageConverter());
        return factory;
    }
}