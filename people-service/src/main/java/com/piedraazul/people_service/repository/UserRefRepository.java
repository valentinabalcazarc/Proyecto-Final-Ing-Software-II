package com.piedraazul.people_service.repository;

import com.piedraazul.people_service.model.UserRef;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRefRepository extends JpaRepository<UserRef, Long> {
    Optional<UserRef> findByCedUser(Long cedUser);
}