package views.Professional;

import views.ViewManager;

public class winPrincipal_Prof extends javax.swing.JFrame {
    
    
    public winPrincipal_Prof() {
        initComponents();
        //setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelPiedraAzul2 = new javax.swing.JLabel();
        button_LogOut = new javax.swing.JButton();
        jPanelMenu1 = new javax.swing.JPanel();
        button_AddAppoinment = new javax.swing.JButton();
        button_AppointmentManage = new javax.swing.JButton();
        jLabelPiedraAzul1 = new javax.swing.JLabel();
        button_ExportAppointments = new javax.swing.JButton();
        button_viewApps = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelTitle.setBackground(new java.awt.Color(102, 102, 255));
        jPanelTitle.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 255), 1, true));

        jLabelTitle.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setText("MENU PRINCIPAL");

        jLabelPiedraAzul2.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabelPiedraAzul2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPiedraAzul2.setText("Profesional");

        button_LogOut.setBackground(new java.awt.Color(204, 204, 255));
        button_LogOut.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        button_LogOut.setText("CERRAR SESIÓN");
        button_LogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_LogOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(jLabelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(button_LogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGap(260, 260, 260)
                .addComponent(jLabelPiedraAzul2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabelTitle))
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(button_LogOut)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPiedraAzul2)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanelMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMenu1.setVerifyInputWhenFocusTarget(false);

        button_AddAppoinment.setBackground(new java.awt.Color(232, 232, 232));
        button_AddAppoinment.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_AddAppoinment.setText("Agendar cita");
        button_AddAppoinment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_AddAppoinmentActionPerformed(evt);
            }
        });

        button_AppointmentManage.setBackground(new java.awt.Color(232, 232, 232));
        button_AppointmentManage.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_AppointmentManage.setText("Gestionar cita");
        button_AppointmentManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_AppointmentManageActionPerformed(evt);
            }
        });

        jLabelPiedraAzul1.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabelPiedraAzul1.setText("Servicios medicos Piedra Azul");

        button_ExportAppointments.setBackground(new java.awt.Color(232, 232, 232));
        button_ExportAppointments.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_ExportAppointments.setText("Exportar citas");
        button_ExportAppointments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_ExportAppointmentsActionPerformed(evt);
            }
        });

        button_viewApps.setBackground(new java.awt.Color(232, 232, 232));
        button_viewApps.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_viewApps.setText("Ver citas");
        button_viewApps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_viewAppsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMenu1Layout = new javax.swing.GroupLayout(jPanelMenu1);
        jPanelMenu1.setLayout(jPanelMenu1Layout);
        jPanelMenu1Layout.setHorizontalGroup(
            jPanelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenu1Layout.createSequentialGroup()
                .addGroup(jPanelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMenu1Layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(jLabelPiedraAzul1))
                    .addGroup(jPanelMenu1Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addGroup(jPanelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMenu1Layout.createSequentialGroup()
                                .addComponent(button_viewApps, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(button_AppointmentManage, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelMenu1Layout.createSequentialGroup()
                                .addComponent(button_AddAppoinment, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(button_ExportAppointments, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMenu1Layout.setVerticalGroup(
            jPanelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenu1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPiedraAzul1)
                .addGap(27, 27, 27)
                .addGroup(jPanelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_ExportAppointments, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_AddAppoinment, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_viewApps, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_AppointmentManage, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_AddAppoinmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_AddAppoinmentActionPerformed
        ViewManager.getInstance().getCreateApp_Prof().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_button_AddAppoinmentActionPerformed

    private void button_ExportAppointmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_ExportAppointmentsActionPerformed
        ViewManager.getInstance().getExport().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_button_ExportAppointmentsActionPerformed

    private void button_AppointmentManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_AppointmentManageActionPerformed
        ViewManager.getInstance().getManageApp_Prof().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_button_AppointmentManageActionPerformed

    private void button_viewAppsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_viewAppsActionPerformed
        ViewManager.getInstance().getViewApps_Prof().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_button_viewAppsActionPerformed

    private void button_LogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_LogOutActionPerformed
        ViewManager.getInstance().getLogin().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_button_LogOutActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_AddAppoinment;
    private javax.swing.JButton button_AppointmentManage;
    private javax.swing.JButton button_ExportAppointments;
    private javax.swing.JButton button_LogOut;
    private javax.swing.JButton button_viewApps;
    private javax.swing.JLabel jLabelPiedraAzul1;
    private javax.swing.JLabel jLabelPiedraAzul2;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelMenu1;
    private javax.swing.JPanel jPanelTitle;
    // End of variables declaration//GEN-END:variables
}
