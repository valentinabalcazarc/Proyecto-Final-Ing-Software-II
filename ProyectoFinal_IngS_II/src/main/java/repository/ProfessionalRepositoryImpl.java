package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Professional;
import DataBase.SQLRepository;

public class ProfessionalRepositoryImpl implements ProfessionalRepository {

    @Override
    public void save(Professional professional) {

        String sql = "INSERT INTO PROFESSIONALS (CODUSER, GENPROF, PHONEPROF, STATUSPROF, " +
                     "TYPEPROF, SPECIALITYPROF, ATTENTIONINTERVAL) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, professional.getCodUser());
            stmt.setString(2, professional.getGenProf());
            stmt.setDouble(3, professional.getPhoneProf());
            stmt.setString(4, professional.getStatusProf());
            stmt.setString(5, professional.getTypeProf());
            stmt.setString(6, professional.getSpecialityProf());
            stmt.setShort(7, professional.getAttentionInterval());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Professional findById(int codProf) {

        String sql = "SELECT p.*, u.* FROM PROFESSIONALS p " +
                     "JOIN USERS u ON p.CODUSER = u.CODUSER " +
                     "WHERE p.CODPROF = ?";

        Professional professional = null;

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codProf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                professional = mapResultSetToProfessional(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professional;
    }

    @Override
    public List<Professional> findAll() {

        String sql = "SELECT p.*, u.* FROM PROFESSIONALS p " +
                     "JOIN USERS u ON p.CODUSER = u.CODUSER";

        List<Professional> professionals = new ArrayList<>();

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                professionals.add(mapResultSetToProfessional(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professionals;
    }

    @Override
    public void update(Professional professional) {

        String sql = "UPDATE PROFESSIONALS SET GENPROF=?, PHONEPROF=?, STATUSPROF=?, " +
                     "TYPEPROF=?, SPECIALITYPROF=?, ATTENTIONINTERVAL=? " +
                     "WHERE CODPROF=?";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professional.getGenProf());
            stmt.setDouble(2, professional.getPhoneProf());
            stmt.setString(3, professional.getStatusProf());
            stmt.setString(4, professional.getTypeProf());
            stmt.setString(5, professional.getSpecialityProf());
            stmt.setShort(6, professional.getAttentionInterval());
            stmt.setInt(7, (int) professional.getCodProf());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int codProf) {

        String sql = "DELETE FROM PROFESSIONALS WHERE CODPROF = ?";

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codProf);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Professional mapResultSetToProfessional(ResultSet rs) throws SQLException {

        return new Professional(
                rs.getDouble("CODPROF"),
                rs.getString("GENPROF"),
                rs.getDouble("PHONEPROF"),
                rs.getString("STATUSPROF"),
                rs.getString("TYPEPROF"),
                rs.getString("SPECIALITYPROF"),
                rs.getShort("ATTENTIONINTERVAL"),

                // Datos heredados de USER
                rs.getInt("CODUSER"),
                rs.getInt("CEDUSER"),
                rs.getString("PASSUSER"),
                rs.getString("NAMEUSER"),
                rs.getString("SECOND_NAMEUSER"),
                rs.getString("LASTNAMEUSER"),
                rs.getString("SECOND_LASTNAMEUSER"),
                rs.getString("STATUSUSER"),
                rs.getString("TYPEUSER"),
                rs.getString("SECURITYQUESTION"),
                rs.getString("SECURITYANSWER")
        );
    }
}
