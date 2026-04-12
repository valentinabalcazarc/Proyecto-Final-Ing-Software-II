package com.piedraazul.auth_service.messaging;

import com.piedraazul.auth_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishUserRegistered(User user) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_QUEUE, user);
    }
}