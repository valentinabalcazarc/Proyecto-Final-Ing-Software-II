package views.Professional;

import com.toedter.calendar.JCalendar;
import configuration.FestivosService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Appointment;
import models.Professional;
import services.ServiceManager;
import views.ViewManager;


public class winCreateAppointment_Prof extends javax.swing.JFrame {

    int citaSeleccionada = -1;
    
    public winCreateAppointment_Prof() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        logicaCalendario();
        
        //logica tabla
        inicializarTablaCitas();
        cargarTabla();
        cargarProfesionales();
        
        cbx_Professional.setSelectedItem(null);
    }
    
    private void logicaCalendario(){
        FestivosService festivosService = new FestivosService();

        JCalendar calendar = jDate_ExportDate.getJCalendar();
        calendar.setWeekOfYearVisible(false);

        // Pintar cuando abre la ventana
        festivosService.pintarDomingos(calendar);
        festivosService.pintarFestivos(calendar);

        // Volver a pintar cuando cambie el mes
        calendar.getMonthChooser().addPropertyChangeListener(evt -> {
            festivosService.pintarDomingos(calendar);
            festivosService.pintarFestivos(calendar);
        });

        // Volver a pintar cuando cambie el año
        calendar.getYearChooser().addPropertyChangeListener(evt -> {
            festivosService.pintarDomingos(calendar);
            festivosService.pintarFestivos(calendar);
        });
        
        //Fechas minima y maxima
        Calendar hoy = Calendar.getInstance();
        Date fechaMinima = hoy.getTime();

        Calendar finDeAño = Calendar.getInstance();
        finDeAño.set(Calendar.MONTH, Calendar.DECEMBER);
        finDeAño.set(Calendar.DAY_OF_MONTH, 31);

        Date fechaMaxima = finDeAño.getTime();

        jDate_ExportDate.setMinSelectableDate(fechaMinima);
        jDate_ExportDate.setMaxSelectableDate(fechaMaxima);
    }
    
    private void inicializarTablaCitas() {
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("Fecha");
        model.addColumn("Hora");
        model.addColumn("Nombre Profesional");
        model.addColumn("Tipo Profesional");
        model.addColumn("Especialidad");

        table_App.setModel(model);
        
    }
    
    private void cargarTabla() {

        DefaultTableModel model = (DefaultTableModel) table_App.getModel();
        model.setRowCount(0);

        List<Object[]> lista = ServiceManager.getInstance().getAppointmentService().getGeneretedAppointments();

        for (Object[] fila : lista) {
            model.addRow(fila);
        }
    }
    
    private void cargarProfesionales() {

        cbx_Professional.removeAllItems();

        List<Professional> lista = ServiceManager.getInstance().getProfessionalService().getAllProfessionals();
        System.out.println("Profesionales encontrados: " + lista.size());
        for (Professional p : lista) {
            cbx_Professional.addItem(p);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_App = new javax.swing.JTable();
        btnContinue = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jDate_ExportDate = new com.toedter.calendar.JDateChooser();
        jDate_ExportDate.getDateEditor().setEnabled(false);
        button_Find = new javax.swing.JButton();
        button_EraseFilter = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbx_Professional = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Cascadia Code", 0, 13)); // NOI18N
        jLabel7.setText("Citas disponibles");

        table_App.setBackground(new java.awt.Color(232, 232, 232));
        table_App.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_App.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_AppMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_App);

        btnContinue.setBackground(new java.awt.Color(70, 175, 65));
        btnContinue.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        btnContinue.setForeground(new java.awt.Color(255, 255, 255));
        btnContinue.setText("Continuar");
        btnContinue.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinueActionPerformed(evt);
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

        jLabel8.setFont(new java.awt.Font("Cascadia Code", 0, 13)); // NOI18N
        jLabel8.setText("Ingrese la fecha de las citas: ");

        jDate_ExportDate.setForeground(new java.awt.Color(232, 232, 232));

        button_Find.setBackground(new java.awt.Color(89, 71, 255));
        button_Find.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_Find.setForeground(new java.awt.Color(255, 255, 255));
        button_Find.setText("Buscar");
        button_Find.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        button_Find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_FindActionPerformed(evt);
            }
        });

        button_EraseFilter.setBackground(new java.awt.Color(89, 71, 255));
        button_EraseFilter.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_EraseFilter.setForeground(new java.awt.Color(255, 255, 255));
        button_EraseFilter.setText("Borrar Filtro");
        button_EraseFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_EraseFilterActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setFont(new java.awt.Font("Cascadia Code", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("AGENDAR CITA MANUAL");

        jLabel2.setFont(new java.awt.Font("Cascadia Code", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Seleccion de fecha");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(329, 329, 329)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel3.setText("Ingrese el médico/terapista:");

        cbx_Professional.setBackground(new java.awt.Color(232, 232, 232));
        cbx_Professional.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button_EraseFilter)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 926, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(button_Find, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(jDate_ExportDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbx_Professional, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(389, 389, 389))))
                .addGap(41, 41, 41))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbx_Professional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jDate_ExportDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Find)
                .addGap(11, 11, 11)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_EraseFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        ViewManager.getInstance().getPrincipalProf().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinueActionPerformed
        if (citaSeleccionada != -1) {
            String fechaStr = table_App.getValueAt(citaSeleccionada, 1).toString();
            String horaStr = table_App.getValueAt(citaSeleccionada, 2).toString();

            Appointment app = new Appointment();
            app.setDate(java.time.LocalDate.parse(fechaStr));
            app.setTime(java.time.LocalTime.parse(horaStr));

            ViewManager.getInstance().getCreateAppPart2_Prof().setApp(app);
            ViewManager.getInstance().getCreateAppPart2_Prof().setVisible(true);
            this.setVisible(false);

        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, seleccione una cita de la tabla.");
        }
    }//GEN-LAST:event_btnContinueActionPerformed

    private void table_AppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_AppMouseClicked
        this.citaSeleccionada = table_App.getSelectedRow();
    }//GEN-LAST:event_table_AppMouseClicked

    private void button_EraseFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_EraseFilterActionPerformed
        cargarTabla();
        cbx_Professional.setSelectedItem(null);
        jDate_ExportDate.setDate(null);
    }//GEN-LAST:event_button_EraseFilterActionPerformed

    private void button_FindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_FindActionPerformed
        Integer codDoc = null;
        LocalDate fecha = null;

        if (cbx_Professional.getSelectedItem() != null) {
            Professional prof = (Professional) cbx_Professional.getSelectedItem();
            codDoc = (int)prof.getCodProf();
        }

        if (jDate_ExportDate.getDate() != null) {
            fecha = jDate_ExportDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        List<Object[]> resultados = ServiceManager.getInstance().getAppointmentService().getGeneretedAppointmentsFiltered(codDoc, fecha);

        DefaultTableModel model = (DefaultTableModel) table_App.getModel();
        model.setRowCount(0);

        for (Object[] fila : resultados) {
            model.addRow(fila);
        }
    }//GEN-LAST:event_button_FindActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContinue;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton button_EraseFilter;
    private javax.swing.JButton button_Find;
    private javax.swing.JComboBox<Professional> cbx_Professional;
    private com.toedter.calendar.JDateChooser jDate_ExportDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_App;
    // End of variables declaration//GEN-END:variables
}
