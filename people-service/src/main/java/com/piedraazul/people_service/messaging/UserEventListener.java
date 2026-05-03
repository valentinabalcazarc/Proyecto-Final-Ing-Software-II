package com.piedraazul.people_service.messaging;

import com.piedraazul.people_service.dto.UserEventDTO;
import com.piedraazul.people_service.model.UserRef;
import com.piedraazul.people_service.repository.UserRefRepository;
import com.piedraazul.people_service.service.UserRefService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final UserRefService userRefService;
    private final UserRefRepository userRefRepository;

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void handleUserRegistered(UserEventDTO dto) {
        try {
            if (userRefService.existsByCodUser(dto.getCodUser())) return;

            UserRef userRef = new UserRef();
            userRef.setCodUser(dto.getCodUser());
            userRef.setCedUser(dto.getCedUser());
            userRef.setNameUser(buildFullName(dto.getNameUser(), dto.getSecondNameUser()));
            userRef.setLastNameUser(buildFullName(dto.getLastNameUser(), dto.getSecondLastNameUser()));
            userRef.setRoleUser(dto.getRoleUser());
            userRefService.save(userRef);
        } catch (Exception e) {
            System.err.println("Error registrando user_ref: " + e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.USER_UPDATED_QUEUE)
    public void handleUserUpdated(UserEventDTO dto) {
        try {
            UserRef userRef = userRefRepository.findById(dto.getCodUser()).orElse(null);
            if (userRef == null) return;

            userRef.setNameUser(buildFullName(dto.getNameUser(), dto.getSecondNameUser()));
            userRef.setLastNameUser(buildFullName(dto.getLastNameUser(), dto.getSecondLastNameUser()));
            userRefService.save(userRef);
        } catch (Exception e) {
            System.err.println("Error actualizando user_ref: " + e.getMessage());
        }
    }

    private String buildFullName(String first, String second) {
        if (second == null || second.isBlank()) return first != null ? first : "";
        return (first + " " + second).trim();
    }
}