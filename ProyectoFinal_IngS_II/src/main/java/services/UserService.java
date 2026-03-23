package services;

import enums.RoleUserEnum;
import models.User;

public interface UserService {

    boolean regUser(User newUser);

    Enum<RoleUserEnum> authUser(int cedUser, String password);

    boolean recoverPassword(int cedUser, String answer);

    boolean updatePassword(int cedUser, String newPassword);
    
    User findByCedUser(int cedUser);
}
