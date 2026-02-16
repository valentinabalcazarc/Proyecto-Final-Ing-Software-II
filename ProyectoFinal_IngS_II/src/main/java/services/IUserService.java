
package services;

import models.User;

/**
 *
 * @author SAM
 */
public interface IUserService {
    public boolean regUser(User newUser);
    public boolean authUser(int cedUser, String password);
    public boolean recoverPassword(int cedUser, String answer);
    public void updatePassword(String newPassword, User user);
}
