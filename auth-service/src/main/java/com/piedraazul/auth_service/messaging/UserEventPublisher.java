package com.piedraazul.auth_service.messaging;

import com.piedraazul.auth_service.dto.UserEventDTO;
import com.piedraazul.auth_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishUserRegistered(User user) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_QUEUE, toDTO(user));
    }

    public void publishUserUpdated(User user) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_UPDATED_QUEUE, toDTO(user));
    }

    public void publishUserDeactivated(User user) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_UPDATED_QUEUE, toDTO(user));
    }

    private UserEventDTO toDTO(User user) {
        return new UserEventDTO(
                user.getCodUser(),
                user.getCedUser(),
                user.getNameUser(),
                user.getSecondNameUser(),
                user.getLastNameUser(),
                user.getSecondLastNameUser(),
                user.getRoleUser().name()
        );
    }
}