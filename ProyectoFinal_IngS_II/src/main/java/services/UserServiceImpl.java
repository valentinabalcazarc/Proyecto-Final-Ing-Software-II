package services;

import configuration.IAutehnticationService;
import models.User;
import repository.UserRepository;

public class UserServiceImpl implements IUserService {
    
    private UserRepository userRepository;
    private IAutehnticationService authService;


    public UserServiceImpl(UserRepository userRepository, IAutehnticationService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Override
    public boolean regUser(User newUser) {
        String passHash = authService.encrypt(newUser.getPassUser());
        String answHash = authService.encrypt(newUser.getSecurityAnswer());
        newUser.setPassUser(passHash);
        newUser.setSecurityAnswer(answHash);
        userRepository.save(newUser);
        return true;
    }

    @Override
    public boolean authUser(int cedUser, String password) {
        User user = userRepository.findByCedUser(cedUser);
        return authService.verify(user.getPassUser(),password);
    }
    
    @Override
    public boolean recoverPassword(int cedUser, String answer){
        User user = userRepository.findByCedUser(cedUser);
        return authService.verify(answer, user.getSecurityAnswer());
    }

    @Override
    public void updatePassword(String newPassword, User user) {
        user.setPassUser(newPassword);
        userRepository.update(user);
    }
    
    

}
