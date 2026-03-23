package repository;

import DataBase.SQLRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Patient;

public class PatientRepositoryImpl implements PatientRepository{
    @Override
    public boolean save(Patient patient) {
        
        String sql = "INSERT INTO PATIENT (IDPATIENT, NAMEPATIENT, SECOND_NAMEPATIENT, " +
                     "LASTNAMEPATIENT, SECOND_LASTNAMEPATIENT, PHONE_PATIENT, DATE_BIRTH_PATIENT, " +
                     "GENDER_PATIENT) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        
        

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            

            stmt.setInt(1, patient.getIdPatient());
            stmt.setString(2, patient.getNamePatient());
            stmt.setString(3, patient.getSecondNamePatient() != null ? patient.getSecondNamePatient() : "");
            stmt.setString(4, patient.getLastNamePatient());
            stmt.setString(5, patient.getSecondLastNamePatient() != null ? patient.getSecondLastNamePatient() : "");
            stmt.setInt(6, patient.getPhonePatient());
            stmt.setObject(7, patient.getDateBirthPatient());
            stmt.setString(8, patient.getGenderPatient());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar paciente", e);
        }
    }

    
    @Override
    public Patient findById(int codPatient) {

        String sql = "SELECT * FROM PATIENT WHERE CODPATIENT = ?";
        Patient patient = null;

        try (Connection conn = SQLRepository.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codPatient);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = mapResultSetToPatient(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patient;
    }

   
    @Override
    public Patient findByCedPatient(int cedPatient) {

        String sql = "SELECT * FROM PATIENT WHERE IDPATIENT = ?";
        Patient patient = null;

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cedPatient);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = mapResultSetToPatient(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patient;
    }

    // =========================
    // LISTAR TODOS
    // =========================
    @Override
    public List<Patient> findAll() {

        String sql = "SELECT * FROM PATIENT";
        List<Patient> patients = new ArrayList<>();

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                patients.add(mapResultSetToPatient(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }
    
    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        Object dateObj = rs.getObject("DATE_BIRTH_PATIENT");
        java.time.LocalDate fechaFinal = null;

        if (dateObj != null) {
            if (dateObj instanceof String) {
                // Caso SQLite: 
                fechaFinal = java.time.LocalDate.parse((String) dateObj);
            } else if (dateObj instanceof java.sql.Date) {
                // Caso MySQL/PostgreSQL:
                fechaFinal = ((java.sql.Date) dateObj).toLocalDate();
            } else if (dateObj instanceof java.sql.Timestamp) {
                // Caso SQL Server: 
                fechaFinal = ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate();
            }
        }

        return new Patient(
                rs.getInt("CODPATIENT"),
                rs.getInt("IDPATIENT"),
                rs.getString("NAMEPATIENT"),
                rs.getString("SECOND_NAMEPATIENT"),
                rs.getString("LASTNAMEPATIENT"),
                rs.getString("SECOND_LASTNAMEPATIENT"),
                rs.getInt("PHONE_PATIENT"),
                fechaFinal,
                rs.getString("GENDER_PATIENT")
        );
    }
}
