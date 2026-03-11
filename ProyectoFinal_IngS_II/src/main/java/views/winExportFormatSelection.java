package views;

import interfacePlugin.IReportPlugin;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AppointmentRep;
import plugin.manager.AppointmentPluginManager;
import services.AppointmentService;

public class winExportFormatSelection extends javax.swing.JFrame {
    private AppointmentService appointmentService;
    private List<AppointmentRep> appointmentReps;
    
    public winExportFormatSelection(AppointmentService appointmentService, List<AppointmentRep> appointmentReps) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.appointmentService = appointmentService;
        this.appointmentReps = appointmentReps;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        button_Json = new javax.swing.JButton();
        button_Html = new javax.swing.JButton();
        button_Back = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea_Export = new javax.swing.JTextArea();

        jScrollPane2.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(89, 71, 255));

        jLabel1.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FORMATO DE EXPORTACIÓN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(227, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(164, 164, 164))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel2.setText("Escoja el formato en el que requiere exportar los datos de las citas médicas");

        button_Json.setBackground(new java.awt.Color(89, 71, 255));
        button_Json.setFont(new java.awt.Font("Cascadia Code", 1, 14)); // NOI18N
        button_Json.setForeground(new java.awt.Color(255, 255, 255));
        button_Json.setText("Formato JSON");
        button_Json.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_JsonActionPerformed(evt);
            }
        });

        button_Html.setBackground(new java.awt.Color(89, 71, 255));
        button_Html.setFont(new java.awt.Font("Cascadia Code", 1, 14)); // NOI18N
        button_Html.setForeground(new java.awt.Color(255, 255, 255));
        button_Html.setText("Formato HTML");
        button_Html.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_HtmlActionPerformed(evt);
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

        textArea_Export.setColumns(20);
        textArea_Export.setRows(5);
        jScrollPane1.setViewportView(textArea_Export);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(button_Json, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(button_Html, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(button_Back))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_Json, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_Html, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(button_Back)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_JsonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_JsonActionPerformed
        try {
            AppointmentPluginManager.init("src/main/resources/");
        } catch (Exception ex) {
            Logger.getLogger(winExport.class.getName()).log(Level.SEVERE, null, ex);
        }

        AppointmentPluginManager manager = AppointmentPluginManager.getInstance();

        IReportPlugin plugin = manager.getReportPlugin("json");

        List<AppointmentRep> datos = appointmentService.getAppointmentForReport();

        String datosPrint = plugin.generateReport(appointmentReps);
        textArea_Export.setText(datosPrint);
        //System.out.println(datosPrint);
    }//GEN-LAST:event_button_JsonActionPerformed

    private void button_HtmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_HtmlActionPerformed
        try {
            AppointmentPluginManager.init("src/main/resources/");
        } catch (Exception ex) {
            Logger.getLogger(winExport.class.getName()).log(Level.SEVERE, null, ex);
        }

        AppointmentPluginManager manager = AppointmentPluginManager.getInstance();

        IReportPlugin plugin = manager.getReportPlugin("html");

        List<AppointmentRep> datos = appointmentService.getAppointmentForReport();

        String datosPrint = plugin.generateReport(appointmentReps);
        textArea_Export.setText(datosPrint);
        //System.out.println(datosPrint);
    }//GEN-LAST:event_button_HtmlActionPerformed

    private void button_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_BackActionPerformed
        this.dispose();
    }//GEN-LAST:event_button_BackActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_Back;
    private javax.swing.JButton button_Html;
    private javax.swing.JButton button_Json;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextArea textArea_Export;
    // End of variables declaration//GEN-END:variables
}
