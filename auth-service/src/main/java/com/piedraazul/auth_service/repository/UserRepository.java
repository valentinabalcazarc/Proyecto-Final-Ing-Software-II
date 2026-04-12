package com.piedraazul.auth_service.repository;

import com.piedraazul.auth_service.enums.RoleUserEnum;
import com.piedraazul.auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCedUser(Long cedUser);
    List<User> findByRoleUser(RoleUserEnum role);
    boolean existsByCedUser(Long cedUser);
}