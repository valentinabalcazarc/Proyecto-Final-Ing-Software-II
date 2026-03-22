package views.Professional;

import com.toedter.calendar.JCalendar;
import configuration.FestivosService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Professional;
import services.ServiceManager;
import views.ViewManager;


public class winViewAppointments_Prof extends javax.swing.JFrame {

    
    public winViewAppointments_Prof() {
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
        
        model.addColumn("Codigo Cita");
        model.addColumn("Fecha");
        model.addColumn("Hora");
        model.addColumn("ID Paciente");
        model.addColumn("Nombre Paciente");
        model.addColumn("Nombre Profesional");
        model.addColumn("Tipo Profesional");
        model.addColumn("Especialidad");
        model.addColumn("Estado");

        table_App.setModel(model);
        
    }
    
    private void cargarTabla() {

        DefaultTableModel model = (DefaultTableModel) table_App.getModel();
        model.setRowCount(0);

        List<Object[]> lista = ServiceManager.getInstance().getAppointmentService().getAppointmentsForTable();

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

        jScrollPane2 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbx_Professional = new javax.swing.JComboBox<>();
        jDate_ExportDate = new com.toedter.calendar.JDateChooser();
        jDate_ExportDate.getDateEditor().setEnabled(false);
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_App = new javax.swing.JTable();
        button_Find = new javax.swing.JButton();
        button_EraseFilter = new javax.swing.JButton();
        button_Back = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelPiedraAzul2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setAutoscrolls(true);

        jLabel2.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel2.setText("Ingrese el médico/terapista:");

        jLabel3.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel3.setText("Seleccione la fecha de las citas: ");

        cbx_Professional.setBackground(new java.awt.Color(232, 232, 232));
        cbx_Professional.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N

        jDate_ExportDate.setForeground(new java.awt.Color(232, 232, 232));

        jLabel4.setFont(new java.awt.Font("Cascadia Code", 1, 14)); // NOI18N
        jLabel4.setText("Citas");

        table_App.setBackground(new java.awt.Color(232, 232, 232));
        table_App.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
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
        table_App.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(table_App);

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

        button_Back.setBackground(new java.awt.Color(232, 232, 232));
        button_Back.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_Back.setText("<- Regresar");
        button_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_BackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel4))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(43, 43, 43)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbx_Professional, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDate_ExportDate, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 998, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_Find, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(button_Back)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_EraseFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbx_Professional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jDate_ExportDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(button_Find)
                .addGap(4, 4, 4)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_EraseFilter)
                    .addComponent(button_Back))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(panel);

        jPanel1.setBackground(new java.awt.Color(89, 71, 255));
        jPanel1.setForeground(new java.awt.Color(0, 102, 204));

        jLabel1.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("VISUALIZACIÓN DE CITAS");

        jLabelPiedraAzul2.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabelPiedraAzul2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPiedraAzul2.setText("Profesional");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(371, 371, 371)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(490, 490, 490)
                        .addComponent(jLabelPiedraAzul2)))
                .addContainerGap(375, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPiedraAzul2)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

        List<Object[]> resultados = ServiceManager.getInstance().getAppointmentService().searchAppointments(codDoc, fecha);

        DefaultTableModel model = (DefaultTableModel) table_App.getModel();
        model.setRowCount(0);

        for (Object[] fila : resultados) {
            model.addRow(fila);
        }
    }//GEN-LAST:event_button_FindActionPerformed

    private void button_EraseFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_EraseFilterActionPerformed
        cargarTabla();
        cbx_Professional.setSelectedItem(null);
        jDate_ExportDate.setDate(null);
    }//GEN-LAST:event_button_EraseFilterActionPerformed

    private void button_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_BackActionPerformed
        ViewManager.getInstance().getPrincipalProf().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_button_BackActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_Back;
    private javax.swing.JButton button_EraseFilter;
    private javax.swing.JButton button_Find;
    private javax.swing.JComboBox<Professional> cbx_Professional;
    private com.toedter.calendar.JDateChooser jDate_ExportDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelPiedraAzul2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panel;
    private javax.swing.JTable table_App;
    // End of variables declaration//GEN-END:variables
}
