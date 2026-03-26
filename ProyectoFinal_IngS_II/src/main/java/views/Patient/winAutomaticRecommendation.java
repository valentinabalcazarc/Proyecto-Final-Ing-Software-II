package views.Patient;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import enums.SpecialityProfEnum;
import javax.swing.JOptionPane;
import models.Appointment;
import models.Patient;
import models.Professional;
import services.ServiceManager;
import views.ViewManager;


public class winAutomaticRecommendation extends javax.swing.JFrame {

    Patient pat;
    SpecialityProfEnum specialityProf;
    Appointment app;
    Professional prof;
    
    public winAutomaticRecommendation() {
        initComponents();
        
    }

    public void setPat(Patient pat) {
        this.pat = pat;
    }

    public void setSpecialityProf(SpecialityProfEnum specialityProf) {
        this.specialityProf = specialityProf;
    }

    public void logica(){
        this.app = ServiceManager.getInstance().getAppointmentService().getFirstAvailableBySpeciality(specialityProf);
        this.prof = ServiceManager.getInstance().getProfessionalService().findByCod(app.getProfessionalId());

        if (app == null || prof == null) {
            lbl_Fecha.setText("No hay citas disponibles próximamente.");
            lbl_Hora.setText("");
            lbl_Profesional.setText("");
            return;
        }

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", new Locale("es", "ES"));
        String fechaFormateada = app.getDate().format(formatoFecha);
        fechaFormateada = fechaFormateada.substring(0, 1).toUpperCase() + fechaFormateada.substring(1);

        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        String horaFormateada = app.getTime().format(formatoHora);

        lbl_Fecha.setText("\uD83D\uDCC5 " + fechaFormateada); 
        lbl_Hora.setText("\uD83D\uDD51 " + horaFormateada);
        lbl_Profesional.setText("\uD83D\uDC64 " + prof.getNameUser() + " " + prof.getLastNameUser());
    }
    
    public void cargarRecomendacion() {
        if (this.specialityProf != null) {
            logica(); 
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelInfo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbl_Fecha = new javax.swing.JLabel();
        lbl_Hora = new javax.swing.JLabel();
        lbl_Profesional = new javax.swing.JLabel();
        jButtonOtherTimes = new javax.swing.JButton();
        jButtonConfirmAppointment = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabelTitle.setFont(new java.awt.Font("Cascadia Code", 1, 18)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setText("Asistente de Agendamiento");

        jLabelInfo.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabelInfo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelInfo.setText("Programa tu cita ahora");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelInfo)
                    .addComponent(jLabelTitle))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelInfo)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel1.setText("La cita mas cercana disponible es:");

        jPanel3.setBackground(new java.awt.Color(221, 221, 221));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_Fecha.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        lbl_Fecha.setText("jLabel2");
        lbl_Fecha.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lbl_Hora.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        lbl_Hora.setText("jLabel3");
        lbl_Hora.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lbl_Profesional.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        lbl_Profesional.setText("jLabel4");
        lbl_Profesional.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_Profesional)
                    .addComponent(lbl_Hora)
                    .addComponent(lbl_Fecha))
                .addContainerGap(467, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbl_Fecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(lbl_Hora)
                .addGap(27, 27, 27)
                .addComponent(lbl_Profesional)
                .addGap(25, 25, 25))
        );

        jButtonOtherTimes.setBackground(new java.awt.Color(217, 79, 79));
        jButtonOtherTimes.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jButtonOtherTimes.setText("Ver otros horarios disponibles ");
        jButtonOtherTimes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOtherTimesActionPerformed(evt);
            }
        });

        jButtonConfirmAppointment.setBackground(new java.awt.Color(102, 204, 0));
        jButtonConfirmAppointment.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jButtonConfirmAppointment.setText("Confirmar cita");
        jButtonConfirmAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmAppointmentActionPerformed(evt);
            }
        });

        btnReturn.setBackground(new java.awt.Color(232, 232, 232));
        btnReturn.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        btnReturn.setText("<- Regresar");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addContainerGap(381, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonOtherTimes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonConfirmAppointment)))
                .addGap(67, 67, 67))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonOtherTimes, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonConfirmAppointment)
                    .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
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
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOtherTimesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOtherTimesActionPerformed
        ViewManager.getInstance().getSelectSpecificApp_Pat().setPat(pat);
        ViewManager.getInstance().getSelectSpecificApp_Pat().setSpeciality(specialityProf);
        ViewManager.getInstance().getSelectSpecificApp_Pat().inicializarVentana();
        ViewManager.getInstance().getSelectSpecificApp_Pat().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButtonOtherTimesActionPerformed

    private void jButtonConfirmAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmAppointmentActionPerformed
        Patient patient = ServiceManager.getInstance().getPatientService().findByCed(pat.getIdPatient());

        if (patient == null) {
            boolean okReg = ServiceManager.getInstance().getPatientService().regPatient(pat);
            if (okReg) {
                patient = ServiceManager.getInstance().getPatientService().findByCed(pat.getIdPatient());
            } else {
                JOptionPane.showMessageDialog(this, "Error crítico: No se pudo registrar al nuevo paciente.");
                return;
            }
        }

        Appointment appointment = new Appointment();
        appointment.setDate(app.getDate());
        appointment.setTime(app.getTime());
        appointment.setProfessionalId((int)prof.getCodProf());
        appointment.setPatientId(patient.getCodPatient()); 

        boolean okApp = ServiceManager.getInstance().getAppointmentService().registerAppointment(app);

        if (okApp) {
            JOptionPane.showMessageDialog(this, "¡Cita guardada con éxito!");
            ViewManager.getInstance().getViewApps_Prof().cargarTabla();
            ViewManager.getInstance().getPrincipalPatient().setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Error: El horario ya no está disponible.");
        }
    }//GEN-LAST:event_jButtonConfirmAppointmentActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        ViewManager.getInstance().getSelectService_Pat().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton jButtonConfirmAppointment;
    private javax.swing.JButton jButtonOtherTimes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_Fecha;
    private javax.swing.JLabel lbl_Hora;
    private javax.swing.JLabel lbl_Profesional;
    // End of variables declaration//GEN-END:variables
}
