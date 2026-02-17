package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.User;
import models.StatusUserEnum;
import models.RoleUserEnum;
import DataBase.SQLRepository;

public class UserRepositoryImpl implements UserRepository {

    // =========================
    // INSERT
    // =========================
    @Override
    public boolean save(User user) {

        String sql = "INSERT INTO USERS (CEDUSER, PASSUSER, NAMEUSER, SECOND_NAMEUSER, " +
                     "LASTNAMEUSER, SECOND_LASTNAMEUSER, STATUSUSER, ROLEUSER, " +
                     "QUESTUSER, ANSWERUSER) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getCedUser());
            stmt.setString(2, user.getPassUser());
            stmt.setString(3, user.getNameUser());
            stmt.setString(4, user.getSecondNameUser());
            stmt.setString(5, user.getLastNameUser());
            stmt.setString(6, user.getSecondLastNameUser());
            stmt.setString(7, user.getStatusUser().name());
            stmt.setString(8, user.getRoleUser().name());
            stmt.setString(9, user.getSecurityQuestion());
            stmt.setString(10, user.getSecurityAnswer());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario", e);
        }
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    @Override
    public User findById(int codUser) {

        String sql = "SELECT * FROM USERS WHERE CODUSER = ?";
        User user = null;

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // =========================
    // BUSCAR POR CÉDULA
    // =========================
    @Override
    public User findByCedUser(int cedUser) {

        String sql = "SELECT * FROM USERS WHERE CEDUSER = ?";
        User user = null;

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cedUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // =========================
    // LISTAR TODOS
    // =========================
    @Override
    public List<User> findAll() {

        String sql = "SELECT * FROM USERS";
        List<User> users = new ArrayList<>();

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // =========================
    // ACTUALIZAR
    // =========================
    @Override
    public boolean update(User user) {

        String sql = "UPDATE USERS SET CEDUSER=?, PASSUSER=?, NAMEUSER=?, SECOND_NAMEUSER=?, " +
                     "LASTNAMEUSER=?, SECOND_LASTNAMEUSER=?, STATUSUSER=?, ROLEUSER=?, " +
                     "QUESTUSER=?, ANSWERUSER=? " +
                     "WHERE CODUSER=?";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getCedUser());
            stmt.setString(2, user.getPassUser());
            stmt.setString(3, user.getNameUser());
            stmt.setString(4, user.getSecondNameUser());
            stmt.setString(5, user.getLastNameUser());
            stmt.setString(6, user.getSecondLastNameUser());
            stmt.setString(7, user.getStatusUser().name());
            stmt.setString(8, user.getRoleUser().name());
            stmt.setString(9, user.getSecurityQuestion());
            stmt.setString(10, user.getSecurityAnswer());
            stmt.setInt(11, user.getCodUser());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    // =========================
    // ELIMINAR
    // =========================
    @Override
    public boolean delete(int codUser) {

        String sql = "DELETE FROM USERS WHERE CODUSER = ?";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codUser);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }

    // =========================
    // MÉTODO PRIVADO DE MAPEO
    // =========================
    private User mapResultSetToUser(ResultSet rs) throws SQLException {

        return new User(
                rs.getInt("CODUSER"),
                rs.getInt("CEDUSER"),
                rs.getString("PASSUSER"),
                rs.getString("NAMEUSER"),
                rs.getString("SECOND_NAMEUSER"),
                rs.getString("LASTNAMEUSER"),
                rs.getString("SECOND_LASTNAMEUSER"),
                StatusUserEnum.valueOf(rs.getString("STATUSUSER")),
                RoleUserEnum.valueOf(rs.getString("ROLEUSER")),
                rs.getString("QUESTUSER"),
                rs.getString("ANSWERUSER")
        );
    }
}
