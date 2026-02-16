package services;

import repository.UserRepository;
import configuration.IAutehnticationService;
import models.User;

public class UserService {
    
    private UserRepository userRepository;
    private IAutehnticationService authService;

    public UserService(UserRepository userRepository, IAutehnticationService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public boolean regUser(User newUser) {
        String hash = authService.encrypt(newUser.getPassUser());
        newUser.setPassUser(hash);
        userRepository.save(newUser);
        return true;
    }

    public boolean authUser(int cedUser, String password) {
        User user = userRepository.findByCedUser(cedUser);
        return authService.verify(user.getPassUser(),password);
    }

}
