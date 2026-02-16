package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.User;
import models.StatusUserEnum;
import models.roleUserEnum;
import DataBase.SQLRepository;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void save(User user) {

        String sql = "INSERT INTO USERS (CEDUSER, PASSUSER, NAMEUSER, SECOND_NAMEUSER, " +
                     "LASTNAMEUSER, SECOND_LASTNAMEUSER, STATUSUSER, TYPEUSER, " +
                     "SECURITYQUESTION, SECURITYANSWER) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getCedUser());
            stmt.setString(2, user.getPassUser());
            stmt.setString(3, user.getNameUser());
            stmt.setString(4, user.getSecondNameUser());
            stmt.setString(5, user.getLastNameUser());
            stmt.setString(6, user.getSecondLastNameUser());

            // ENUM → STRING
            stmt.setString(7, user.getStatusUser().name());
            stmt.setString(8, user.getRoleUser().name());

            stmt.setString(9, user.getSecurityQuestion());
            stmt.setString(10, user.getSecurityAnswer());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public void update(User user) {

        String sql = "UPDATE USERS SET CEDUSER=?, PASSUSER=?, NAMEUSER=?, SECOND_NAMEUSER=?, " +
                     "LASTNAMEUSER=?, SECOND_LASTNAMEUSER=?, STATUSUSER=?, TYPEUSER=?, " +
                     "SECURITYQUESTION=?, SECURITYANSWER=? " +
                     "WHERE CODUSER=?";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getCedUser());
            stmt.setString(2, user.getPassUser());
            stmt.setString(3, user.getNameUser());
            stmt.setString(4, user.getSecondNameUser());
            stmt.setString(5, user.getLastNameUser());
            stmt.setString(6, user.getSecondLastNameUser());

            // ENUM → STRING
            stmt.setString(7, user.getStatusUser().name());
            stmt.setString(8, user.getRoleUser().name());

            stmt.setString(9, user.getSecurityQuestion());
            stmt.setString(10, user.getSecurityAnswer());

            stmt.setInt(11, user.getCodUser());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int codUser) {

        String sql = "DELETE FROM USERS WHERE CODUSER = ?";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codUser);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {

        return new User(
                rs.getInt("CODUSER"),
                rs.getInt("CEDUSER"),
                rs.getString("PASSUSER"),
                rs.getString("NAMEUSER"),
                rs.getString("SECOND_NAMEUSER"),
                rs.getString("LASTNAMEUSER"),
                rs.getString("SECOND_LASTNAMEUSER"),

                // STRING → ENUM
                StatusUserEnum.valueOf(rs.getString("STATUSUSER")),
                roleUserEnum.valueOf(rs.getString("TYPEUSER")),

                rs.getString("SECURITYQUESTION"),
                rs.getString("SECURITYANSWER")
        );
    }
}
