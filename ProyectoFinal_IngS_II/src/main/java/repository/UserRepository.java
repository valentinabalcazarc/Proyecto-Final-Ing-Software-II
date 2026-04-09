package repository;

import java.util.List;
import models.User;

public interface UserRepository {

    boolean save(User user);

    User findById(int codUser);

    User findByCedUser(int cedUser);

    List<User> findAll();

    boolean update(User user);

    boolean delete(int codUser);
}

