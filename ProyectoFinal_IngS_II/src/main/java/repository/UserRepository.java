package repository;

import java.util.List;
import models.User;

public interface UserRepository {

    void save(User user);

    User findById(int codUser);

    User findByCedUser(int cedUser);

    List<User> findAll();

    void update(User user);

    void delete(int codUser);
}


