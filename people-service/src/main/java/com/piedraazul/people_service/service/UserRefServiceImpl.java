package com.piedraazul.people_service.service;

import com.piedraazul.people_service.model.UserRef;
import com.piedraazul.people_service.repository.UserRefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRefServiceImpl implements UserRefService {

    private final UserRefRepository userRefRepository;

    @Override
    public UserRef save(UserRef userRef) {
        return userRefRepository.save(userRef);
    }

    @Override
    public boolean existsByCodUser(Long codUser) {
        return userRefRepository.existsById(codUser);
    }
}