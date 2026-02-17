package services;

import models.User;
import repository.UserRepository;
import configuration.IAuthenticationService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final IAuthenticationService authService;

    public UserServiceImpl(UserRepository userRepository, IAuthenticationService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Override
    public boolean regUser(User newUser) {

        if (newUser == null 
                || newUser.getPassUser() == null 
                || newUser.getSecurityAnswer() == null) {
            return false;
        }

        if (userRepository.findByCedUser(newUser.getCedUser()) != null) {
            return false;
        }

        String passHash = authService.encrypt(newUser.getPassUser());
        String answerHash = authService.encrypt(newUser.getSecurityAnswer());

        newUser.setPassUser(passHash);
        newUser.setSecurityAnswer(answerHash);

        return userRepository.save(newUser);
    }

    @Override
    public boolean authUser(int cedUser, String password) {

        if (password == null) {
            return false;
        }
        
        String hash = authService.encrypt(password);
        User user = userRepository.findByCedUser(cedUser);

        if (user == null) {
            return false;
        }

        return authService.verify(user, hash);
    }

    @Override
    public boolean recoverPassword(int cedUser, String answer) {

        if (answer == null) {
            return false;
        }

        String hash = authService.encrypt(answer);
        User user = userRepository.findByCedUser(cedUser);

        if (user == null) {
            return false;
        }

        return authService.verify(user, hash);
    }

    @Override
    public boolean updatePassword(int cedUser, String newPassword) {

        if (newPassword == null) {
            return false;
        }

        User user = userRepository.findByCedUser(cedUser);

        if (user == null) {
            return false;
        }

        String passHash = authService.encrypt(newPassword);
        user.setPassUser(passHash);

        return userRepository.update(user);
    }
}
