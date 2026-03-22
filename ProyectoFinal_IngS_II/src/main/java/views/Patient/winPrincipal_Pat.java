package views.Patient;

import views.winLogin;

public class winPrincipal_Pat extends javax.swing.JFrame {

    public winPrincipal_Pat() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelPiedraAzul = new javax.swing.JLabel();
        btn_CerrarSesion = new javax.swing.JButton();
        jPanelMenu = new javax.swing.JPanel();
        button_AddAppoinment = new javax.swing.JButton();
        button_ViewAppointment = new javax.swing.JButton();
        jLabelPiedraAzul1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelTitle.setBackground(new java.awt.Color(102, 102, 255));
        jPanelTitle.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 255), 1, true));

        jLabelTitle.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setText("MENU PRINCIPAL");

        jLabelPiedraAzul.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabelPiedraAzul.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPiedraAzul.setText("Paciente");

        btn_CerrarSesion.setBackground(new java.awt.Color(204, 204, 255));
        btn_CerrarSesion.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        btn_CerrarSesion.setText("CERRAR SESIÓN");
        btn_CerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitle)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                        .addComponent(jLabelPiedraAzul)
                        .addGap(75, 75, 75)))
                .addGap(60, 60, 60)
                .addComponent(btn_CerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabelTitle))
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_CerrarSesion)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelPiedraAzul)
                .addContainerGap())
        );

        jPanelMenu.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMenu.setVerifyInputWhenFocusTarget(false);

        button_AddAppoinment.setBackground(new java.awt.Color(249, 249, 249));
        button_AddAppoinment.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_AddAppoinment.setText("Agendar cita");
        button_AddAppoinment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_AddAppoinmentActionPerformed(evt);
            }
        });

        button_ViewAppointment.setBackground(new java.awt.Color(249, 249, 249));
        button_ViewAppointment.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_ViewAppointment.setText("Ver citas");
        button_ViewAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_ViewAppointmentActionPerformed(evt);
            }
        });

        jLabelPiedraAzul1.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabelPiedraAzul1.setText("Servicios medicos Piedra Azul");

        javax.swing.GroupLayout jPanelMenuLayout = new javax.swing.GroupLayout(jPanelMenu);
        jPanelMenu.setLayout(jPanelMenuLayout);
        jPanelMenuLayout.setHorizontalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMenuLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(button_AddAppoinment, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                .addComponent(button_ViewAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
            .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMenuLayout.createSequentialGroup()
                    .addContainerGap(205, Short.MAX_VALUE)
                    .addComponent(jLabelPiedraAzul1)
                    .addGap(185, 185, 185)))
        );
        jPanelMenuLayout.setVerticalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_AddAppoinment, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_ViewAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMenuLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabelPiedraAzul1)
                    .addContainerGap(85, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CerrarSesionActionPerformed
        new winLogin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_CerrarSesionActionPerformed

    private void button_AddAppoinmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_AddAppoinmentActionPerformed
        winPatientInfo_Pat infoPatient = new winPatientInfo_Pat();
        infoPatient.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_button_AddAppoinmentActionPerformed

    private void button_ViewAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_ViewAppointmentActionPerformed
        winViewAppoinment_Pat viewApp = new winViewAppoinment_Pat();
        viewApp.setVisible(true);
    }//GEN-LAST:event_button_ViewAppointmentActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_CerrarSesion;
    private javax.swing.JButton button_AddAppoinment;
    private javax.swing.JButton button_ViewAppointment;
    private javax.swing.JLabel jLabelPiedraAzul;
    private javax.swing.JLabel jLabelPiedraAzul1;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPanel jPanelTitle;
    // End of variables declaration//GEN-END:variables
}
