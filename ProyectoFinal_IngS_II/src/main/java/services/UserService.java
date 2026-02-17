package services;

import models.User;

public interface UserService {

    boolean regUser(User newUser);

    boolean authUser(int cedUser, String password);

    boolean recoverPassword(int cedUser, String answer);

    boolean updatePassword(int cedUser, String newPassword);
}
