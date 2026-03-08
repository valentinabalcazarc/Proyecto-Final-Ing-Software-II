package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DataBase.SQLRepository;
import enums.StatusAppointment;
import java.time.LocalDate;
import models.Appointment;
import models.AppointmentRep;

public class AppointmentRepositoryImpl implements AppointmentRepository {
    
    @Override
    public boolean save(Appointment appointment) {

        String sql = "INSERT INTO APPOINTMENT (CODPATIENT, CODPROF, DATEAPP, TIMEAPP, DESCAPP, STATUSAPP) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getProfessionalId());
            stmt.setDate(3, Date.valueOf(appointment.getDate()));
            stmt.setTime(4, Time.valueOf(appointment.getTime()));
            stmt.setString(5, appointment.getDescription());

            // guardar enum como String
            stmt.setString(6, appointment.getStatus().name());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario", e);
        }
    }
    
    @Override
    public List<Appointment> findAll() {

        String sql =
            "SELECT a.CODAPP, a.CODPATIENT, a.CODPROF, a.DATEAPP, a.TIMEAPP, a.DESCAPP, a.STATUSAPP " +
            "FROM APPOINTMENT a " +
            "ORDER BY a.DATEAPP";

        List<Appointment> appointments = new ArrayList<>();

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Appointment app = new Appointment(
                        rs.getInt("CODAPP"),
                        rs.getInt("CODPATIENT"),
                        rs.getInt("CODPROF"),
                        rs.getDate("DATEAPP").toLocalDate(),
                        rs.getTime("TIMEAPP").toLocalTime(),
                        rs.getString("DESCAPP"),
                        StatusAppointment.valueOf(rs.getString("STATUSAPP"))
                );

                appointments.add(app);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }
    
    public List<Object[]> findAllForTable() {

        String sql =
            "SELECT a.DATEAPP, a.TIMEAPP, a.STATUSAPP," +
            "p.IDPATIENT, p.NAMEPATIENT, p.LASTNAMEPATIENT, " +
            "u.NAMEUSER, u.LASTNAMEUSER, " +
            "pr.TYPEPROF, pr.SPECIALITYPROF " +
            "FROM APPOINTMENT a " +
            "JOIN PATIENT p ON a.CODPATIENT = p.CODPATIENT " +
            "JOIN PROFESSIONAL pr ON a.CODPROF = pr.CODPROF " +
            "JOIN USERS u ON pr.CODUSER = u.CODUSER " +
            "ORDER BY a.DATEAPP";

        List<Object[]> lista = new ArrayList<>();

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Object[] fila = {
                    rs.getString("DATEAPP"),
                    rs.getString("TIMEAPP"),
                    rs.getInt("IDPATIENT"),
                    rs.getString("NAMEPATIENT") + " " + rs.getString("LASTNAMEPATIENT"),
                    rs.getString("NAMEUSER") + " " + rs.getString("LASTNAMEUSER"),
                    rs.getString("TYPEPROF"),
                    rs.getString("SPECIALITYPROF"),
                    rs.getString("STATUSAPP")
                };

                lista.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    @Override
    public List<AppointmentRep> findAllForReport() {

        List<AppointmentRep> lista = new ArrayList<>();

        String sql = "SELECT a.CODAPP, a.DATEAPP, " +
                    "COALESCE(p.NAMEPATIENT,'') || ' ' || " +
                    "COALESCE(p.SECOND_NAMEPATIENT,'') || ' ' || " +
                    "COALESCE(p.LASTNAMEPATIENT,'') || ' ' || " +
                    "COALESCE(p.SECOND_LASTNAMEPATIENT,'') AS FULL_NAME_PATIENT, " +
                    "p.IDPATIENT, " +
                    "COALESCE(u.NAMEUSER,'') || ' ' || " +
                    "COALESCE(u.SECOND_NAMEUSER,'') || ' ' || " +
                    "COALESCE(u.LASTNAMEUSER,'') || ' ' || " +
                    "COALESCE(u.SECOND_LASTNAMEUSER,'') AS FULL_NAME_PROFF " +
                    "FROM APPOINTMENT a " +
                    "JOIN PATIENT p ON a.CODPATIENT = p.CODPATIENT " +
                    "JOIN PROFESSIONAL pr ON a.CODPROF = pr.CODPROF " +
                    "JOIN USERS u ON pr.CODUSER = u.CODUSER " +
                    "ORDER BY a.CODAPP";

        try (Connection conn = SQLRepository.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                String dateStr = rs.getString("DATEAPP");
                LocalDate fecha = null;
                if (dateStr != null && !dateStr.isEmpty()) {
                    fecha = LocalDate.parse(dateStr);
                }

                AppointmentRep appRep = new AppointmentRep();
                appRep.setCodApp(rs.getInt("CODAPP"));
                appRep.setDate(fecha);
                appRep.setNamePat(rs.getString("FULL_NAME_PATIENT"));
                appRep.setIdPat(rs.getInt("IDPATIENT"));
                appRep.setNameProff(rs.getString("FULL_NAME_PROFF"));

                lista.add(appRep);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Aquí puedes lanzar una excepción personalizada si lo deseas
        }

        return lista;
    }
    
    @Override
    public List<Object[]> findFiltered(Integer codProf, LocalDate fecha) {

        List<Object[]> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT a.DATEAPP, a.TIMEAPP, a.STATUSAPP," +
            "p.IDPATIENT, p.NAMEPATIENT, p.LASTNAMEPATIENT, " +
            "u.NAMEUSER, u.LASTNAMEUSER, " +
            "pr.TYPEPROF, pr.SPECIALITYPROF " +
            "FROM APPOINTMENT a " +
            "JOIN PATIENT p ON a.CODPATIENT = p.CODPATIENT " +
            "JOIN PROFESSIONAL pr ON a.CODPROF = pr.CODPROF " +
            "JOIN USERS u ON pr.CODUSER = u.CODUSER "
        );

        List<Object> params = new ArrayList<>();
        boolean whereAdded = false;

        if (codProf != null) {
            sql.append(whereAdded ? " AND " : " WHERE ");
            sql.append("a.CODPROF = ?");
            params.add(codProf);
            whereAdded = true;
        }

        if (fecha != null) {
            sql.append(whereAdded ? " AND " : " WHERE ");
            sql.append("a.DATEAPP = ?");
            params.add(fecha.toString());
        }

        sql.append(" ORDER BY a.DATEAPP");

        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Object[] fila = {
                    rs.getString("DATEAPP"),
                    rs.getString("TIMEAPP"),
                    rs.getInt("IDPATIENT"),
                    rs.getString("NAMEPATIENT") + " " + rs.getString("LASTNAMEPATIENT"),
                    rs.getString("NAMEUSER") + " " + rs.getString("LASTNAMEUSER"),
                    rs.getString("TYPEPROF"),
                    rs.getString("SPECIALITYPROF"),
                    rs.getString("STATUSAPP")
                };

                lista.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}