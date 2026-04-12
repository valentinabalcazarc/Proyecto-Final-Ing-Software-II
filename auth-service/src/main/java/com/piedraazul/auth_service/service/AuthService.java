package com.piedraazul.auth_service.service;

import com.piedraazul.auth_service.dto.LoginRequestDTO;
import com.piedraazul.auth_service.dto.LoginResponseDTO;
import com.piedraazul.auth_service.dto.RegisterUserDTO;
import com.piedraazul.auth_service.dto.UpdateUserDTO;
import com.piedraazul.auth_service.enums.RoleUserEnum;
import com.piedraazul.auth_service.model.User;
import java.util.List;
import java.util.Optional;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
    User register(RegisterUserDTO dto);
    Optional<User> findByCedula(Long cedUser);
    List<User> findByRole(RoleUserEnum role);
    User update(Long id, UpdateUserDTO dto);
    void deactivate(Long id);
    boolean existsByCedula(Long cedUser);
}