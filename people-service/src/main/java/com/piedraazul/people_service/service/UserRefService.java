package com.piedraazul.people_service.service;

import com.piedraazul.people_service.model.UserRef;

public interface UserRefService {
    UserRef save(UserRef userRef);
    boolean existsByCodUser(Long codUser);
}