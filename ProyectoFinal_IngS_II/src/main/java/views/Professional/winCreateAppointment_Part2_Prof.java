package views.Professional;

import com.toedter.calendar.JCalendar;
import configuration.FestivosService;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import models.Appointment;
import models.Patient;
import services.ServiceManager;
import views.ViewManager;


public class winCreateAppointment_Part2_Prof extends javax.swing.JFrame {
    
    Appointment app;

    public winCreateAppointment_Part2_Prof() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        logicaCalendario();
        inicializaciones();
        
        limpiarCampos();
        ocultarErrores();
    }

    public void setApp(Appointment app) {
        this.app = app;
        if (app != null) {
            txt_DateAndTimeAppointment.setText(app.getDate().toString() + " | " + app.getTime().toString());
        }
    }
    
    private void logicaCalendario(){
        FestivosService festivosService = new FestivosService();

        JCalendar calendar = jDate_BirthDate.getJCalendar();
        calendar.setWeekOfYearVisible(false);

        // Pintar cuando abre la ventana
        festivosService.pintarFindeSemana(calendar);
        festivosService.pintarFestivos(calendar);

        // Volver a pintar cuando cambie el mes
        calendar.getMonthChooser().addPropertyChangeListener(evt -> {
            festivosService.pintarFindeSemana(calendar);
            festivosService.pintarFestivos(calendar);
        });

        // Volver a pintar cuando cambie el año
        calendar.getYearChooser().addPropertyChangeListener(evt -> {
            festivosService.pintarFindeSemana(calendar);
            festivosService.pintarFestivos(calendar);
        });
        
        //Fechas minima y maxima
        Calendar hoy = Calendar.getInstance();
        Date fechaMaxima = hoy.getTime();

        Calendar haceCienAnios = Calendar.getInstance();
        haceCienAnios.add(Calendar.YEAR, -100);
        Date fechaMinima = haceCienAnios.getTime();

        jDate_BirthDate.setMinSelectableDate(fechaMinima);
        jDate_BirthDate.setMaxSelectableDate(fechaMaxima);
    }

    public int calcularEdad(java.time.LocalDate fechaNac) {
        if (fechaNac == null) return 0;
        return java.time.Period.between(fechaNac, java.time.LocalDate.now()).getYears();
    }
    
    private void limpiarCampos() {
        txt_Cedula.setText("");
        tF_userFirstName.setText("");
        tF_userSecondName.setText("");
        tF_userFirstLastName.setText("");
        tF_userSecondLastName.setText("");
        txt_PhoneNumber.setText("");
        txt_Observation.setText(""); 
        cbx_Gender.setSelectedIndex(0);
        jDate_BirthDate.setDate(null);
        lbl_Edad.setText("");
    }
    
    private void ocultarErrores() {
        lb_errorFistName.setVisible(false);
        lb_errorSecondName.setVisible(false);
        lb_errorFistLastName.setVisible(false);
        lb_errorSecondLastName.setVisible(false);
        lb_errorID.setVisible(false);
        lb_errorCelular.setVisible(false);
        lb_errorFecha.setVisible(false);
    }
    
    private void inicializaciones(){
        configurarValidacion(txt_Cedula, lb_errorID, "NUMERICO", true);
        configurarValidacion(tF_userFirstName, lb_errorFistName, "TEXTO", true);
        configurarValidacion(tF_userFirstLastName, lb_errorFistLastName, "TEXTO", true);
        configurarValidacion(txt_PhoneNumber, lb_errorCelular, "NUMERICO", true);
        
        configurarValidacion(tF_userSecondName, lb_errorSecondName, "TEXTO", false);
        configurarValidacion(tF_userSecondLastName, lb_errorSecondLastName, "TEXTO", false);

        jDate_BirthDate.addPropertyChangeListener("date", evt -> {
            if (jDate_BirthDate.getDate() != null) {
                lb_errorFecha.setVisible(false);
                
                java.time.LocalDate fechaNac = convertirDateALocalDate(jDate_BirthDate.getDate());
                lbl_Edad.setText(String.valueOf(calcularEdad(fechaNac)));
            } else {
                lb_errorFecha.setVisible(true);
                lbl_Edad.setText("");
            }
        });
    }
    
    private void configurarValidacion(javax.swing.JTextField field, javax.swing.JLabel label, String tipo, boolean esObligatorio) {
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String texto = field.getText().trim();
                boolean hayError = false;
                String mensaje = "";

                if (texto.isEmpty()) {
                    if (esObligatorio) {
                        hayError = true;
                        mensaje = "Campo requerido";
                    }
                } else {
                    if (tipo.equals("NUMERICO") && !texto.matches("\\d+")) {
                        hayError = true;
                        mensaje = "Solo números";
                    } else if (tipo.equals("TEXTO") && !esNombreValido(texto)) {
                        hayError = true;
                        mensaje = "Solo letras";
                    }
                }
                label.setText(mensaje);
                label.setVisible(hayError);
            }
        });
    }
    
    private boolean esNombreValido(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    }
    
    public java.time.LocalDate convertirDateALocalDate(java.util.Date date) {
        if (date == null) return null;
        return date.toInstant()
                   .atZone(java.time.ZoneId.systemDefault())
                   .toLocalDate();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tF_userSecondName5 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnSaveAppointment = new javax.swing.JButton();
        txt_Cedula = new javax.swing.JTextField();
        lb_errorFistName = new javax.swing.JLabel();
        lb_errorFistLastName = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lb_errorSecondLastName = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tF_userFirstLastName = new javax.swing.JTextField();
        tF_userSecondLastName = new javax.swing.JTextField();
        tF_userSecondName = new javax.swing.JTextField();
        lb_errorSecondName = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_PhoneNumber = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_DateAndTimeAppointment = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btnReturn = new javax.swing.JButton();
        btn_Find = new javax.swing.JButton();
        tF_userFirstName = new javax.swing.JTextField();
        cbx_Gender = new javax.swing.JComboBox<>();
        jDate_BirthDate = new com.toedter.calendar.JDateChooser();
        jDate_BirthDate.getDateEditor().setEnabled(false);
        jLabel20 = new javax.swing.JLabel();
        lbl_Edad = new javax.swing.JLabel();
        lb_errorCelular = new javax.swing.JLabel();
        txt_Observation = new javax.swing.JTextField();
        lb_errorID = new javax.swing.JLabel();
        lb_errorFecha = new javax.swing.JLabel();

        jLabel18.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel18.setText("Fecha y hora de la cita:");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setFont(new java.awt.Font("Cascadia Code", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("AGENDAR CITA");

        jLabel2.setFont(new java.awt.Font("Cascadia Code", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Innformación del paciente");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Cascadia Code", 0, 13)); // NOI18N
        jLabel6.setText("Ingrese la cedula del paciente: ");

        btnSaveAppointment.setBackground(new java.awt.Color(70, 175, 65));
        btnSaveAppointment.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        btnSaveAppointment.setText("Guardar cita");
        btnSaveAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAppointmentActionPerformed(evt);
            }
        });

        lb_errorFistName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorFistName.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorFistName.setText("Error");

        lb_errorFistLastName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorFistLastName.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorFistLastName.setText("Error");

        jLabel9.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel9.setText("Primer nombre:");

        lb_errorSecondLastName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorSecondLastName.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorSecondLastName.setText("Error");

        jLabel7.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel7.setText("Segundo nombre:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(169, 169, 169));
        jLabel10.setText("(Opcional)");

        jLabel11.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel11.setText("Primer apellido:");

        jLabel12.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel12.setText("Segundo apellido:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(169, 169, 169));
        jLabel13.setText("(Opcional)");

        lb_errorSecondName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorSecondName.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorSecondName.setText("Error");

        jLabel14.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel14.setText("Genero:");

        jLabel15.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel15.setText("Celular:");

        jLabel16.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel16.setText("Fecha de nacimiento:");

        jLabel17.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel17.setText("Fecha y hora de la cita:");

        jLabel19.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel19.setText("Observación:");

        btnReturn.setBackground(new java.awt.Color(232, 232, 232));
        btnReturn.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        btnReturn.setText("<- Regresar");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        btn_Find.setBackground(new java.awt.Color(89, 71, 255));
        btn_Find.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        btn_Find.setForeground(new java.awt.Color(255, 255, 255));
        btn_Find.setText("Buscar");
        btn_Find.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btn_Find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FindActionPerformed(evt);
            }
        });

        cbx_Gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", "Other" }));

        jDate_BirthDate.setForeground(new java.awt.Color(232, 232, 232));

        jLabel20.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel20.setText("Edad:");

        lbl_Edad.setBackground(new java.awt.Color(255, 255, 255));
        lbl_Edad.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        lbl_Edad.setText(".");

        lb_errorCelular.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorCelular.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorCelular.setText("Error");

        lb_errorID.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorID.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorID.setText("Error");

        lb_errorFecha.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorFecha.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorFecha.setText("Error");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7))
                                        .addGap(53, 53, 53)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tF_userSecondName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lb_errorSecondName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lb_errorFistName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tF_userFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel19))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(tF_userSecondLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tF_userFirstLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lb_errorSecondLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_errorFistLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(245, 245, 245)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel20))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_Observation, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_DateAndTimeAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_Edad, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_PhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbx_Gender, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_errorCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_errorID, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txt_Cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(btn_Find, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(38, 38, 38)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_errorFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDate_BirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(122, 122, 122)))
                        .addContainerGap(148, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSaveAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_Cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Find))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_errorID)
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel7)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lb_errorFistName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tF_userSecondName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(lb_errorSecondName))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(tF_userFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tF_userFirstLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lb_errorFistLastName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tF_userSecondLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_errorSecondLastName)))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_PhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(cbx_Gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_errorCelular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jDate_BirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_errorFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(lbl_Edad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_DateAndTimeAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(txt_Observation, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSaveAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_FindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FindActionPerformed
        try {
            int cedPatient = Integer.parseInt(txt_Cedula.getText());
            Patient patient = ServiceManager.getInstance().getPatientService().findByCed(cedPatient);

            if (patient != null) {
                tF_userFirstName.setText(patient.getNamePatient());
                tF_userSecondName.setText(patient.getSecondNamePatient() != null ? patient.getSecondNamePatient() : "");
                tF_userFirstLastName.setText(patient.getLastNamePatient());
                tF_userSecondLastName.setText(patient.getSecondLastNamePatient() != null ? patient.getSecondLastNamePatient() : "");
                cbx_Gender.setSelectedItem(patient.getGenderPatient());
                txt_PhoneNumber.setText(Integer.toString(patient.getPhonePatient()));

                if (patient.getDateBirthPatient() != null) {
                    java.util.Date date = java.sql.Date.valueOf(patient.getDateBirthPatient());
                    jDate_BirthDate.setDate(date);

                    lbl_Edad.setText(String.valueOf(calcularEdad(patient.getDateBirthPatient())));
                }

                if (app != null) {
                    txt_DateAndTimeAppointment.setText(app.getDate().toString() + " | " + app.getTime().toString());
                }

            } else {
                JOptionPane.showMessageDialog(this, "El paciente no fue encontrado. Llene los datos manualmente.");
                
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número de cédula válido.");
        }
    }//GEN-LAST:event_btn_FindActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        ViewManager.getInstance().getCreateApp_Prof().setVisible(true);
        this.setVisible(false);
        limpiarCampos();
        ocultarErrores();
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnSaveAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAppointmentActionPerformed
        if (lb_errorID.isVisible() || lb_errorFistName.isVisible() || 
            lb_errorFistLastName.isVisible() || lb_errorCelular.isVisible() ||
            lb_errorSecondName.isVisible() || lb_errorSecondLastName.isVisible() ||
            lb_errorFecha.isVisible()) {

            JOptionPane.showMessageDialog(this, "Por favor, corrija los errores.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try{
            int cedPatient = Integer.parseInt(txt_Cedula.getText());
            Patient patient = ServiceManager.getInstance().getPatientService().findByCed(cedPatient);
            
            if (patient == null){
                patient = new Patient();
                patient.setIdPatient(cedPatient);
                patient.setNamePatient(tF_userFirstName.getText());
                patient.setSecondNamePatient(tF_userSecondName.getText());
                patient.setLastNamePatient(tF_userFirstLastName.getText());
                patient.setSecondLastNamePatient(tF_userSecondLastName.getText());
                patient.setPhonePatient(Integer.parseInt(txt_PhoneNumber.getText()));
                patient.setDateBirthPatient(convertirDateALocalDate(jDate_BirthDate.getDate()));
                patient.setGenderPatient(cbx_Gender.getSelectedItem().toString());
                
                boolean regPatient = ServiceManager.getInstance().getPatientService().regPatient(patient);
            
                if(regPatient){
                    patient = ServiceManager.getInstance().getPatientService().findByCed(cedPatient);
                    app.setPatientId(patient.getCodPatient());
                    app.setDescription(txt_Observation.getText().trim());

                    boolean ok = ServiceManager.getInstance().getAppointmentService().registerAppointment(app);

                    if (ok) {
                        int respuesta = javax.swing.JOptionPane.showOptionDialog(
                                    this, 
                                    "¡Cita guardada con éxito!", 
                                    "Confirmación", 
                                    javax.swing.JOptionPane.DEFAULT_OPTION, 
                                    javax.swing.JOptionPane.INFORMATION_MESSAGE, 
                                    null, 
                                    new Object[]{"OK"}, 
                                    "OK"
                            );
                        if (respuesta == javax.swing.JOptionPane.OK_OPTION || respuesta == javax.swing.JOptionPane.CLOSED_OPTION) {
                            limpiarCampos();
                            ocultarErrores();
                            ViewManager.getInstance().getPrincipalProf().setVisible(true);
                            this.setVisible(false); 
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al registrar cita.");
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "Error al registrar paciente.");
                }
                
            }
            if (patient != null) {
                app.setPatientId(patient.getCodPatient()); 
                app.setDescription(txt_Observation.getText().trim());

                boolean ok = ServiceManager.getInstance().getAppointmentService().registerAppointment(app);

                if (ok) {
                    int respuesta = javax.swing.JOptionPane.showOptionDialog(
                                this, 
                                "¡Cita guardada con éxito!", 
                                "Confirmación", 
                                javax.swing.JOptionPane.DEFAULT_OPTION, 
                                javax.swing.JOptionPane.INFORMATION_MESSAGE, 
                                null, 
                                new Object[]{"OK"}, 
                                "OK"
                        );
                    if (respuesta == javax.swing.JOptionPane.OK_OPTION || respuesta == javax.swing.JOptionPane.CLOSED_OPTION) {
                        limpiarCampos();
                        ocultarErrores();
                        ViewManager.getInstance().getPrincipalProf().setVisible(true);
                        this.setVisible(false); 
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar cita.");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado.");
        }
    }//GEN-LAST:event_btnSaveAppointmentActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnSaveAppointment;
    private javax.swing.JButton btn_Find;
    private javax.swing.JComboBox<String> cbx_Gender;
    private com.toedter.calendar.JDateChooser jDate_BirthDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lb_errorCelular;
    private javax.swing.JLabel lb_errorFecha;
    private javax.swing.JLabel lb_errorFistLastName;
    private javax.swing.JLabel lb_errorFistName;
    private javax.swing.JLabel lb_errorID;
    private javax.swing.JLabel lb_errorSecondLastName;
    private javax.swing.JLabel lb_errorSecondName;
    private javax.swing.JLabel lbl_Edad;
    private javax.swing.JTextField tF_userFirstLastName;
    private javax.swing.JTextField tF_userFirstName;
    private javax.swing.JTextField tF_userSecondLastName;
    private javax.swing.JTextField tF_userSecondName;
    private javax.swing.JTextField tF_userSecondName5;
    private javax.swing.JTextField txt_Cedula;
    private javax.swing.JTextField txt_DateAndTimeAppointment;
    private javax.swing.JTextField txt_Observation;
    private javax.swing.JTextField txt_PhoneNumber;
    // End of variables declaration//GEN-END:variables
}
