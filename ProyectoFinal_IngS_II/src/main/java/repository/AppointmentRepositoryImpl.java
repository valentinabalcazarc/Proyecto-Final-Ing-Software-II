package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DataBase.SQLRepository;
import enums.SpecialityProfEnum;
import enums.StatusAppointment;
import filters.IFilter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import models.Appointment;
import models.AppointmentRep;

public class AppointmentRepositoryImpl implements AppointmentRepository {
    private static int HORA_FIN = 16;
    
    @Override
    public boolean save(Appointment appointment) {

        String sql = "INSERT INTO APPOINTMENT (CODPATIENT, CODPROF, DATEAPP, TIMEAPP, DESCAPP, STATUSAPP) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = SQLRepository.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getProfessionalId());
            stmt.setString(3, appointment.getDate().toString()); 
            stmt.setString(4, appointment.getTime().toString());
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
    
    @Override
    public List<Object[]> findAllForTable() {

        String sql =
            "SELECT a.CODAPP, a.DATEAPP, a.TIMEAPP, " +
            "p.IDPATIENT, p.NAMEPATIENT, p.LASTNAMEPATIENT, " +
            "u.NAMEUSER, u.LASTNAMEUSER, " +
            "pr.TYPEPROF, pr.SPECIALITYPROF, a.STATUSAPP " +
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
                    rs.getInt("CODAPP"),
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
                appRep.setIdPat(rs.getString("IDPATIENT"));
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
            "SELECT a.CODAPP, a.DATEAPP, a.TIMEAPP, " +
            "p.IDPATIENT, p.NAMEPATIENT, p.LASTNAMEPATIENT, " +
            "u.NAMEUSER, u.LASTNAMEUSER, " +
            "pr.TYPEPROF, pr.SPECIALITYPROF, a.STATUSAPP " +
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
                    rs.getInt("CODAPP"),
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
    public List<Object[]> generateAppForTable() {
        List<Object[]> apps = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        String sqlProf = "SELECT pr.CODPROF, u.NAMEUSER, u.LASTNAMEUSER, pr.TYPEPROF, " +
                         "pr.SPECIALITYPROF, pr.ATTENTIONINTERVAL " +
                         "FROM PROFESSIONAL pr " +
                         "JOIN USERS u ON pr.CODUSER = u.CODUSER " +
                         "WHERE pr.STATUSPROF = 'Active'";

        String sqlOcupadas = "SELECT CODPROF, TIMEAPP FROM APPOINTMENT WHERE DATEAPP = ? AND STATUSAPP = 'Scheduled'";

        try (Connection conn = SQLRepository.conectar()) {

            java.util.Map<String, String> ocupadas = new java.util.HashMap<>();
            try (PreparedStatement stOcup = conn.prepareStatement(sqlOcupadas)) {
                stOcup.setString(1, today.toString());
                ResultSet rsOcup = stOcup.executeQuery();
                while (rsOcup.next()) {
                    String llave = rsOcup.getInt("CODPROF") + "-" + rsOcup.getString("TIMEAPP");
                    ocupadas.put(llave, "Scheduled");
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlProf);
                 ResultSet rs = stmt.executeQuery()) {

                LocalTime startDay = LocalTime.of(7, 0);
                LocalTime endDay = LocalTime.of(HORA_FIN, 0);

                while (rs.next()) {
                    int interval = rs.getInt("ATTENTIONINTERVAL");
                    int codProf = rs.getInt("CODPROF");
                    String profName = rs.getString("NAMEUSER") + " " + rs.getString("LASTNAMEUSER");

                    LocalTime currentTime = startDay;

                    while (currentTime.plusMinutes(interval).isBefore(endDay.plusMinutes(1))) {

                        if (currentTime.isAfter(now)) { 
                            String horaActualStr = currentTime.toString();
                            String llaveBusqueda = codProf + "-" + horaActualStr;

                            if (!ocupadas.containsKey(llaveBusqueda)) {
                                apps.add(new Object[]{
                                    today.toString(),
                                    horaActualStr,
                                    codProf,
                                    profName,
                                    rs.getString("TYPEPROF"),
                                    rs.getString("SPECIALITYPROF")
                                });
                            }
                        }
                        currentTime = currentTime.plusMinutes(interval);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apps;
    }
    
    
    
    @Override
    public List<Object[]> filterGeneretedApp(Integer codProf, LocalDate fecha) {
        List<Object[]> allSlots = new ArrayList<>();
        LocalDate targetDate = (fecha != null) ? fecha : LocalDate.now();
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        String sqlProf = "SELECT pr.CODPROF, u.NAMEUSER, u.LASTNAMEUSER, pr.TYPEPROF, " +
                         "pr.SPECIALITYPROF, pr.ATTENTIONINTERVAL " +
                         "FROM PROFESSIONAL pr " +
                         "JOIN USERS u ON pr.CODUSER = u.CODUSER " +
                         "WHERE pr.STATUSPROF = 'Active'";

        if (codProf != null) {
            sqlProf += " AND pr.CODPROF = " + codProf;
        }

        
        String sqlOcupadas = "SELECT CODPROF, TIMEAPP FROM APPOINTMENT WHERE DATEAPP = ? AND STATUSAPP = 'Scheduled'";

        try (Connection conn = SQLRepository.conectar()) {

            java.util.Set<String> ocupadas = new java.util.HashSet<>();
            try (PreparedStatement stOcup = conn.prepareStatement(sqlOcupadas)) {
                stOcup.setString(1, targetDate.toString());
                ResultSet rsOcup = stOcup.executeQuery();
                while (rsOcup.next()) {
                    ocupadas.add(rsOcup.getInt("CODPROF") + "-" + rsOcup.getString("TIMEAPP"));
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlProf);
                 ResultSet rs = stmt.executeQuery()) {

                java.time.LocalTime startDay = java.time.LocalTime.of(7, 0);
                java.time.LocalTime endDay = java.time.LocalTime.of(HORA_FIN, 0);

                while (rs.next()) {
                    int interval = rs.getInt("ATTENTIONINTERVAL");
                    int currentCodProf = rs.getInt("CODPROF");
                    String profName = rs.getString("NAMEUSER") + " " + rs.getString("LASTNAMEUSER");

                    java.time.LocalTime currentTime = startDay;

                    while (currentTime.plusMinutes(interval).isBefore(endDay.plusMinutes(1))) {
                        boolean esHoraValida = true;
                        if (targetDate.equals(today)) {
                            if (!currentTime.isAfter(now)) {
                                esHoraValida = false;
                            }
                        }
                        if (esHoraValida) { 
                            String horaActual = currentTime.toString();
                            String llave = currentCodProf + "-" + horaActual;

                            if (!ocupadas.contains(llave)) {
                                allSlots.add(new Object[]{
                                    targetDate.toString(),
                                    horaActual,
                                    currentCodProf,
                                    profName,
                                    rs.getString("TYPEPROF"),
                                    rs.getString("SPECIALITYPROF")
                                });
                            }
                        }
                        currentTime = currentTime.plusMinutes(interval);
                        
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allSlots;
    }
    
    @Override
    public Appointment findFirstAvailableBySpeciality(SpecialityProfEnum speciality) {
        LocalDate dateSearch = LocalDate.now();
        LocalDate limitDate = dateSearch.plusDays(60);
        LocalTime now = LocalTime.now();

        String sqlProf = "SELECT pr.CODPROF, pr.ATTENTIONINTERVAL " +
                         "FROM PROFESSIONAL pr " +
                         "WHERE pr.SPECIALITYPROF = ? AND pr.STATUSPROF = 'Active'";

        String sqlOcupadas = "SELECT TIMEAPP FROM APPOINTMENT WHERE DATEAPP = ? AND CODPROF = ? AND STATUSAPP = 'Scheduled'";

        try (Connection conn = SQLRepository.conectar()) {
            while (dateSearch.isBefore(limitDate)) {
                // Saltamos fines de semana
                if (dateSearch.getDayOfWeek() == java.time.DayOfWeek.SUNDAY || dateSearch.getDayOfWeek() == java.time.DayOfWeek.SATURDAY) {
                    dateSearch = dateSearch.plusDays(1);
                    continue;
                }

                try (PreparedStatement stProf = conn.prepareStatement(sqlProf)) {
                    stProf.setString(1, speciality.toString());
                    ResultSet rsProf = stProf.executeQuery();

                    while (rsProf.next()) {
                        int codProf = rsProf.getInt("CODPROF");
                        int interval = rsProf.getInt("ATTENTIONINTERVAL");

                        Set<String> ocupadas = new HashSet<>();
                        try (PreparedStatement stOcup = conn.prepareStatement(sqlOcupadas)) {
                            stOcup.setString(1, dateSearch.toString());
                            stOcup.setInt(2, codProf);
                            ResultSet rsOcup = stOcup.executeQuery();
                            while (rsOcup.next()) {
                                ocupadas.add(rsOcup.getString("TIMEAPP"));
                            }
                        }

                        LocalTime currentTime = LocalTime.of(7, 0);
                        LocalTime endDay = LocalTime.of(HORA_FIN, 0);

                        while (!currentTime.plusMinutes(interval).isAfter(endDay)) {

                            boolean esHoraValida = true;
                            if (dateSearch.equals(LocalDate.now())) {
                                if (!currentTime.isAfter(now)) {
                                    esHoraValida = false;
                                }
                            }

                            if (esHoraValida) {
                                String horaStr = currentTime.toString();
                                if (!ocupadas.contains(horaStr)) {
                                    Appointment available = new Appointment();
                                    available.setDate(dateSearch);
                                    available.setTime(currentTime);
                                    available.setProfessionalId(codProf);
                                    return available; 
                                }
                            }
                            currentTime = currentTime.plusMinutes(interval);
                        }
                    }
                }
                dateSearch = dateSearch.plusDays(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    @Override
    public List<Object[]> generateAppBySpeciality(SpecialityProfEnum speciality) {
        List<Object[]> apps = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        LocalDate targetDate = today;

        String sqlProf = "SELECT pr.CODPROF, u.NAMEUSER, u.LASTNAMEUSER, pr.TYPEPROF, " +
                         "pr.SPECIALITYPROF, pr.ATTENTIONINTERVAL " +
                         "FROM PROFESSIONAL pr " +
                         "JOIN USERS u ON pr.CODUSER = u.CODUSER " +
                         "WHERE pr.STATUSPROF = 'Active' " + 
                         "AND pr.SPECIALITYPROF = ?";

        String sqlOcupadas = "SELECT CODPROF, TIMEAPP FROM APPOINTMENT WHERE DATEAPP = ? AND STATUSAPP = 'Scheduled'";

        try (Connection conn = SQLRepository.conectar()) {

            java.util.Map<String, Boolean> ocupadas = new java.util.HashMap<>();
            try (PreparedStatement stOcup = conn.prepareStatement(sqlOcupadas)) {
                // Buscamos ocupadas para la fecha específica
                stOcup.setString(1, targetDate.toString());
                try (ResultSet rsOcup = stOcup.executeQuery()) {
                    while (rsOcup.next()) {
                        String llave = rsOcup.getInt("CODPROF") + "-" + rsOcup.getString("TIMEAPP");
                        ocupadas.put(llave, true);
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlProf)) {
                stmt.setString(1, speciality.toString());

                try (ResultSet rs = stmt.executeQuery()) {
                    LocalTime startDay = LocalTime.of(7, 0);
                    LocalTime endDay = LocalTime.of(HORA_FIN, 0);

                    while (rs.next()) {
                        int interval = rs.getInt("ATTENTIONINTERVAL");
                        int codProf = rs.getInt("CODPROF");
                        String profName = rs.getString("NAMEUSER") + " " + rs.getString("LASTNAMEUSER");
                        String typeProf = rs.getString("TYPEPROF");
                        String specStr = rs.getString("SPECIALITYPROF");

                        LocalTime currentTime = startDay;

                        while (!currentTime.plusMinutes(interval).isAfter(endDay)) {

                            boolean esHoraValida = true;
                            if (targetDate.equals(today)) {
                                if (!currentTime.isAfter(now)) {
                                    esHoraValida = false;
                                }
                            }

                            if (esHoraValida) { 
                                String horaActual = currentTime.toString();
                                String llaveBusqueda = codProf + "-" + horaActual;

                                if (!ocupadas.containsKey(llaveBusqueda)) {
                                    apps.add(new Object[]{
                                        targetDate.toString(),
                                        horaActual,
                                        codProf,
                                        profName,
                                        typeProf,
                                        specStr
                                    });
                                }
                            }
                            currentTime = currentTime.plusMinutes(interval);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en generación de agenda: " + e.getMessage());
        }
        return apps;
    }
    
    @Override
    public List<Object[]> filterGeneratedAppBySpeciality(Integer codProf, LocalDate fecha, SpecialityProfEnum speciality) {
        List<Object[]> allSlots = new ArrayList<>();
        LocalDate targetDate = (fecha != null) ? fecha : LocalDate.now();
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        StringBuilder sqlProf = new StringBuilder(
            "SELECT pr.CODPROF, u.NAMEUSER, u.LASTNAMEUSER, pr.TYPEPROF, " +
            "pr.SPECIALITYPROF, pr.ATTENTIONINTERVAL " +
            "FROM PROFESSIONAL pr " +
            "JOIN USERS u ON pr.CODUSER = u.CODUSER " +
            "WHERE pr.STATUSPROF = 'Active' " +
            "AND pr.SPECIALITYPROF = ?"
        );

        if (codProf != null) {
            sqlProf.append(" AND pr.CODPROF = ?");
        }

        String sqlOcupadas = "SELECT CODPROF, TIMEAPP FROM APPOINTMENT WHERE DATEAPP = ? AND STATUSAPP = 'Scheduled'";

        try (Connection conn = SQLRepository.conectar()) {

            java.util.Set<String> ocupadas = new java.util.HashSet<>();
            try (PreparedStatement stOcup = conn.prepareStatement(sqlOcupadas)) {
                stOcup.setString(1, targetDate.toString());
                try (ResultSet rsOcup = stOcup.executeQuery()) {
                    while (rsOcup.next()) {
                        ocupadas.add(rsOcup.getInt("CODPROF") + "-" + rsOcup.getString("TIMEAPP"));
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlProf.toString())) {
                stmt.setString(1, speciality.toString());
                if (codProf != null) {
                    stmt.setInt(2, codProf);
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    java.time.LocalTime startDay = java.time.LocalTime.of(7, 0);
                    java.time.LocalTime endDay = java.time.LocalTime.of(HORA_FIN, 0);

                    while (rs.next()) {
                        int interval = rs.getInt("ATTENTIONINTERVAL");
                        int currentCodProf = rs.getInt("CODPROF");
                        String profFull = rs.getString("NAMEUSER") + " " + rs.getString("LASTNAMEUSER");
                        String type = rs.getString("TYPEPROF");
                        String spec = rs.getString("SPECIALITYPROF");

                        java.time.LocalTime currentTime = startDay;

                        while (!currentTime.plusMinutes(interval).isAfter(endDay)) {
                            boolean esHoraValida = true;
                            if (targetDate.equals(today)) {
                                if (!currentTime.isAfter(now)) {
                                    esHoraValida = false;
                                }
                            }
                            
                            if (esHoraValida) {
                                String horaActual = currentTime.toString();
                                String llave = currentCodProf + "-" + horaActual;

                                if (!ocupadas.contains(llave)) {
                                    allSlots.add(new Object[]{
                                        targetDate.toString(),
                                        horaActual,
                                        currentCodProf,
                                        profFull,
                                        type,
                                        spec
                                    });
                                }
                            }
                            currentTime = currentTime.plusMinutes(interval);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error filtrando agenda: " + e.getMessage());
            e.printStackTrace();
        }
        return allSlots;
    }
}