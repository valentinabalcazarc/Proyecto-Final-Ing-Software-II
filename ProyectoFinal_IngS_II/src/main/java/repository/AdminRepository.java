package repository;

import models.Admin;
import java.util.List;

public interface AdminRepository {

    void save(Admin admin);
    Admin findByUser(String adminUser);
    List<Admin> findAll();
    void update(Admin admin);
    void delete(String adminUser);
}
