package com.piedraazul.people_service.messaging;

import com.piedraazul.people_service.model.UserRef;
import com.piedraazul.people_service.service.UserRefService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final UserRefService userRefService;

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void handleUserRegistered(Map<String, Object> message) {
        try {
            UserRef userRef = new UserRef();
            userRef.setCodUser(Long.valueOf(message.get("codUser").toString()));
            userRef.setCedUser(Long.valueOf(message.get("cedUser").toString()));

            // Concatenar nombres
            String firstName = message.get("nameUser") != null ? message.get("nameUser").toString() : "";
            String secondName = message.get("secondNameUser") != null ? message.get("secondNameUser").toString() : "";
            userRef.setNameUser((firstName + " " + secondName).trim());

            // Concatenar apellidos
            String lastName = message.get("lastNameUser") != null ? message.get("lastNameUser").toString() : "";
            String secondLastName = message.get("secondLastNameUser") != null ? message.get("secondLastNameUser").toString() : "";
            userRef.setLastNameUser((lastName + " " + secondLastName).trim());

            userRef.setRoleUser(message.get("roleUser").toString());
            userRef.setStatusUser(message.get("statusUser").toString());

            if (!userRefService.existsByCodUser(userRef.getCodUser())) {
                userRefService.save(userRef);
            }
        } catch (Exception e) {
            System.err.println("Error procesando mensaje: " + e.getMessage());
        }
    }
}