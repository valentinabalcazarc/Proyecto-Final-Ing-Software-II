package com.piedraazul.app_client.services;

import com.piedraazul.app_client.enums.RoleUserEnum;
import com.piedraazul.app_client.models.User;

public interface UserService {

    boolean regUser(User newUser);

    RoleUserEnum authUser(Long cedUser, String password);

    boolean recoverPassword(int cedUser, String answer);

    boolean updatePassword(int cedUser, String newPassword);
    
    User findByCedUser(Long cedUser);
}
