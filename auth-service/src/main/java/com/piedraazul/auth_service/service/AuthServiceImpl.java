package com.piedraazul.auth_service.service;

import com.piedraazul.auth_service.dto.LoginRequestDTO;
import com.piedraazul.auth_service.dto.LoginResponseDTO;
import com.piedraazul.auth_service.dto.RegisterUserDTO;
import com.piedraazul.auth_service.dto.UpdateUserDTO;
import com.piedraazul.auth_service.enums.RoleUserEnum;
import com.piedraazul.auth_service.enums.StatusUserEnum;
import com.piedraazul.auth_service.messaging.UserEventPublisher;
import com.piedraazul.auth_service.model.User;
import com.piedraazul.auth_service.repository.UserRepository;
import com.piedraazul.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserEventPublisher userEventPublisher;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByCedUser(request.getCedUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassUser())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (user.getStatusUser() == StatusUserEnum.Inactive) {
            throw new RuntimeException("Usuario inactivo");
        }

        String token = jwtUtil.generateToken(user.getCedUser().toString(), user.getRoleUser().name());
        return new LoginResponseDTO(token, user.getRoleUser().name(), user.getCodUser(), user.getNameUser());
    }

    @Override
    public User register(RegisterUserDTO dto) {
        if (userRepository.existsByCedUser(dto.getCedUser())) {
            throw new RuntimeException("Ya existe un usuario con esa cédula");
        }
        User user = new User();
        user.setCedUser(dto.getCedUser());
        user.setPassUser(passwordEncoder.encode(dto.getPassUser()));
        user.setNameUser(dto.getNameUser());
        user.setSecondNameUser(dto.getSecondNameUser());
        user.setLastNameUser(dto.getLastNameUser());
        user.setSecondLastNameUser(dto.getSecondLastNameUser());
        user.setRoleUser(dto.getRoleUser());
        user.setSecurityQuestion(dto.getSecurityQuestion());
        user.setSecurityAnswer(dto.getSecurityAnswer());
        user.setStatusUser(StatusUserEnum.Active);
        User saved = userRepository.save(user);

        userEventPublisher.publishUserRegistered(saved);
        return saved;
    }

    @Override
    public Optional<User> findByCedula(Long cedUser) {
        return userRepository.findByCedUser(cedUser);
    }

    @Override
    public List<User> findByRole(RoleUserEnum role) {
        return userRepository.findByRoleUser(role);
    }

    @Override
    public User update(Long id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (dto.getNameUser() != null) user.setNameUser(dto.getNameUser());
        if (dto.getSecondNameUser() != null) user.setSecondNameUser(dto.getSecondNameUser());
        if (dto.getLastNameUser() != null) user.setLastNameUser(dto.getLastNameUser());
        if (dto.getSecondLastNameUser() != null) user.setSecondLastNameUser(dto.getSecondLastNameUser());
        if (dto.getPassUser() != null) user.setPassUser(passwordEncoder.encode(dto.getPassUser()));
        if (dto.getSecurityQuestion() != null) user.setSecurityQuestion(dto.getSecurityQuestion());
        if (dto.getSecurityAnswer() != null) user.setSecurityAnswer(dto.getSecurityAnswer());
        if (dto.getStatusUser() != null) user.setStatusUser(dto.getStatusUser());
        User updated = userRepository.save(user);
        userEventPublisher.publishUserUpdated(updated);
        return updated;
    }

    @Override
    public void deactivate(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setStatusUser(StatusUserEnum.Inactive);
        userRepository.save(user);

        User saved = userRepository.save(user);
        userEventPublisher.publishUserDeactivated(saved);
    }

    @Override
    public boolean existsByCedula(Long cedUser) {
        return userRepository.existsByCedUser(cedUser);
    }
}