package views.Patient;
import models.Professional;

public class winSelectSpecificAppointment_Pat extends javax.swing.JFrame {

    
    public winSelectSpecificAppointment_Pat() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelInfo = new javax.swing.JLabel();
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
        button_Export = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(89, 71, 255));
        jPanel1.setForeground(new java.awt.Color(0, 102, 204));

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

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setAutoscrolls(true);

        jLabel2.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel2.setText("Ingrese el médico/terapista:");

        jLabel3.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel3.setText("Seleccione la fecha: ");

        cbx_Professional.setBackground(new java.awt.Color(232, 232, 232));
        cbx_Professional.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N

        jDate_ExportDate.setForeground(new java.awt.Color(232, 232, 232));

        jLabel4.setFont(new java.awt.Font("Cascadia Code", 1, 14)); // NOI18N
        jLabel4.setText("Citas Disponibles");

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
        button_Back.setText("Regresar");

        button_Export.setBackground(new java.awt.Color(70, 175, 65));
        button_Export.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        button_Export.setText("Confirmar cita");
        button_Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_ExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_Find, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(button_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_Export)
                .addGap(137, 137, 137))
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button_EraseFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(43, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_EraseFilter)
                .addGap(46, 46, 46)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_Export)
                    .addComponent(button_Back))
                .addContainerGap(136, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 934, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_FindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_FindActionPerformed
        
    }//GEN-LAST:event_button_FindActionPerformed

    private void button_EraseFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_EraseFilterActionPerformed
        
    }//GEN-LAST:event_button_EraseFilterActionPerformed

    private void button_ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_ExportActionPerformed

    }//GEN-LAST:event_button_ExportActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_Back;
    private javax.swing.JButton button_EraseFilter;
    private javax.swing.JButton button_Export;
    private javax.swing.JButton button_Find;
    private javax.swing.JComboBox<Professional> cbx_Professional;
    private com.toedter.calendar.JDateChooser jDate_ExportDate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panel;
    private javax.swing.JTable table_App;
    // End of variables declaration//GEN-END:variables
}
