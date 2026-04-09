package views.Patient;

import enums.SpecialityProfEnum;
import javax.swing.JOptionPane;
import models.Patient;
import views.ViewManager;


public class winSelectService_Pat extends javax.swing.JFrame {

    Patient pat;
    SpecialityProfEnum specialityProf;
    
    public winSelectService_Pat() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void setPat(Patient pat) {
        this.pat = pat;
    }
    
    private void navegarAAutoRecomendacion(SpecialityProfEnum seleccion) {
        ViewManager.getInstance().getAutoRecomendation().setPat(pat);
        ViewManager.getInstance().getAutoRecomendation().setSpecialityProf(seleccion);
        ViewManager.getInstance().getAutoRecomendation().cargarRecomendacion();
        ViewManager.getInstance().getAutoRecomendation().setVisible(true);
        this.setVisible(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelInfo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonChiropractic = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaNeutralTherapy = new javax.swing.JTextArea();
        jButtonNeutralTherapy = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaChiropractic = new javax.swing.JTextArea();
        jButtonPhysiotherapy = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaPhysiotherapy = new javax.swing.JTextArea();
        button_Back = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setText("Asistente de agendamiento");

        jLabelInfo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Selecciona tu servicio:");

        jButtonChiropractic.setBackground(new java.awt.Color(102, 153, 0));
        jButtonChiropractic.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonChiropractic.setForeground(new java.awt.Color(255, 255, 255));
        jButtonChiropractic.setText("Quiropractica");
        jButtonChiropractic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChiropracticActionPerformed(evt);
            }
        });

        jTextAreaNeutralTherapy.setBackground(new java.awt.Color(245, 212, 115));
        jTextAreaNeutralTherapy.setColumns(20);
        jTextAreaNeutralTherapy.setRows(5);
        jTextAreaNeutralTherapy.setText("Tratamiento que ayuda a aliviar dolores e \ninflamaciones mediante pequeñas aplicaciones\nque buscan mejorar el funcionamiento del cuerpo\n y el sistema nervioso.");
        jScrollPane1.setViewportView(jTextAreaNeutralTherapy);

        jButtonNeutralTherapy.setBackground(new java.awt.Color(204, 153, 0));
        jButtonNeutralTherapy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonNeutralTherapy.setForeground(new java.awt.Color(255, 255, 255));
        jButtonNeutralTherapy.setText("Terapia neutral");
        jButtonNeutralTherapy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNeutralTherapyActionPerformed(evt);
            }
        });

        jTextAreaChiropractic.setBackground(new java.awt.Color(176, 210, 108));
        jTextAreaChiropractic.setColumns(20);
        jTextAreaChiropractic.setRows(5);
        jTextAreaChiropractic.setText("Terapia manual que ayuda a aliviar dolores\nde espalda, cuello y articulaciones, \nmejorando la postura y el movimiento.");
        jScrollPane2.setViewportView(jTextAreaChiropractic);

        jButtonPhysiotherapy.setBackground(new java.awt.Color(153, 51, 0));
        jButtonPhysiotherapy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonPhysiotherapy.setForeground(new java.awt.Color(255, 255, 255));
        jButtonPhysiotherapy.setText("Fisioterapia");
        jButtonPhysiotherapy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPhysiotherapyActionPerformed(evt);
            }
        });

        jTextAreaPhysiotherapy.setBackground(new java.awt.Color(232, 154, 115));
        jTextAreaPhysiotherapy.setColumns(20);
        jTextAreaPhysiotherapy.setRows(5);
        jTextAreaPhysiotherapy.setText("Tratamiento con ejercicios y movimientos suaves\nque ayuda a recuperar fuerza, movilidad y \ndisminuir el dolor después de una lesión o\n molestia.");
        jScrollPane3.setViewportView(jTextAreaPhysiotherapy);

        button_Back.setBackground(new java.awt.Color(232, 232, 232));
        button_Back.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_Back.setText("<- Regresar");
        button_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_BackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtonPhysiotherapy, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtonChiropractic, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtonNeutralTherapy, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(button_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(jButtonNeutralTherapy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(jButtonChiropractic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jButtonPhysiotherapy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(button_Back)
                .addContainerGap(16, Short.MAX_VALUE))
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

    private void jButtonNeutralTherapyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNeutralTherapyActionPerformed
        navegarAAutoRecomendacion(SpecialityProfEnum.Neural_Therapy);
    }//GEN-LAST:event_jButtonNeutralTherapyActionPerformed

    private void jButtonChiropracticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChiropracticActionPerformed
        navegarAAutoRecomendacion(SpecialityProfEnum.Chiropractor);
    }//GEN-LAST:event_jButtonChiropracticActionPerformed

    private void jButtonPhysiotherapyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPhysiotherapyActionPerformed
        navegarAAutoRecomendacion(SpecialityProfEnum.Physiotherapy);
    }//GEN-LAST:event_jButtonPhysiotherapyActionPerformed

    private void button_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_BackActionPerformed
        ViewManager.getInstance().getInfoPatient_Pat().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_button_BackActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_Back;
    private javax.swing.JButton jButtonChiropractic;
    private javax.swing.JButton jButtonNeutralTherapy;
    private javax.swing.JButton jButtonPhysiotherapy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextAreaChiropractic;
    private javax.swing.JTextArea jTextAreaNeutralTherapy;
    private javax.swing.JTextArea jTextAreaPhysiotherapy;
    // End of variables declaration//GEN-END:variables
}
