package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Admin;
import DataBase.SQLRepository;

public class AdminRepositoryImpl implements AdminRepository {

    @Override
    public void save(Admin admin) {

        String sql = "INSERT INTO ADMIN (ADMINUSER, ADMINPASSWORD) VALUES (?, ?)";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getAdminUser());
            stmt.setString(2, admin.getAdminPassword());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Admin findByUser(String adminUser) {

        String sql = "SELECT * FROM ADMIN WHERE ADMINUSER = ?";
        Admin admin = null;

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adminUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                admin = mapResultSetToAdmin(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admin;
    }

    @Override
    public List<Admin> findAll() {

        String sql = "SELECT * FROM ADMIN";
        List<Admin> admins = new ArrayList<>();

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                admins.add(mapResultSetToAdmin(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admins;
    }

    @Override
    public void update(Admin admin) {

        String sql = "UPDATE ADMIN SET ADMINPASSWORD = ? WHERE ADMINUSER = ?";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getAdminPassword());
            stmt.setString(2, admin.getAdminUser());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String adminUser) {

        String sql = "DELETE FROM ADMIN WHERE ADMINUSER = ?";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adminUser);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar de mapeo
    private Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {

        return new Admin(
                rs.getString("ADMINUSER"),
                rs.getString("ADMINPASSWORD")
        );
    }
}
