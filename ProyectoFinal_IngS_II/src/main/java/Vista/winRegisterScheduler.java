
package Vista;

import javax.swing.JOptionPane;
import models.StatusUserEnum;
import models.TypeProfEnum;
import models.User;
import models.RoleUserEnum;
import services.UserService;

public class winRegisterScheduler extends javax.swing.JFrame {

    UserService iuserservice;
    private boolean mostrarPassword = false;
    
    public winRegisterScheduler() {
        initComponents();
    }
    
    private boolean validarCampos(){
        // validación de vacios importantes
        
        if (txtNumCedula_RSche1.getText().trim().isEmpty() ||
            txtPassword.getPassword().length == 0 ||
            txtConfPass_RSched.getPassword().length == 0 ||
            txtFirstName_RScheduler.getText().trim().isEmpty() ||
            txtSecondN_RSched.getText().trim().isEmpty() ||
            txtFirstLastName_RSched.getText().trim().isEmpty() ||
            txtSecondLastName_RSched.getText().trim().isEmpty() ||
            txtRASecurity_RSched.getText().trim().isEmpty()
            ){
            
            JOptionPane.showMessageDialog(this,
                    "Debe llenar al menos: Cédula, Contraseña, Primer Nombre y Primer Apellido.");
            return false;
            
        }
        
        //validar la cédula numerica
        try{
            Long.valueOf(txtNumCedula_RSche1.getText().trim());
        }catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cédula debe contener solo números.");
            return false;
        }
        
        // validar cadena sin numeros
        if (!esNombreValido(txtFirstName_RScheduler.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El primer nombre contiene caracteres inválidos.");
            return false;
        }

        if (!txtSecondN_RSched.getText().trim().isEmpty() &&
            !esNombreValido(txtSecondN_RSched.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El segundo nombre contiene caracteres inválidos.");
            return false;
        }

        if (!esNombreValido(txtFirstLastName_RSched.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El primer apellido contiene caracteres inválidos.");
            return false;
        }

        if (!txtSecondLastName_RSched.getText().trim().isEmpty() &&
            !esNombreValido(txtSecondLastName_RSched.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El segundo apellido contiene caracteres inválidos.");
            return false;
        }
        
        if (!txtRASecurity_RSched.getText().trim().isEmpty() &&
            !esNombreValido(txtRASecurity_RSched.getText().trim())) {
            JOptionPane.showMessageDialog(this, "La respuesta de seguridad contiene caracteres inválidos.");
            return false;
        }
        
        if(!(txtPassword.equals(txtConfPass_RSched))){
            JOptionPane.showMessageDialog(this, "La contraseña no concuerda con su confirmación.");
            return false;
        }

        return true;
    }
    
    private boolean esNombreValido(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        msgRegisterScheduler = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtConfPass_RSched = new javax.swing.JPasswordField();
        txtFirstName_RScheduler = new javax.swing.JTextField();
        txtFirstLastName_RSched = new javax.swing.JTextField();
        txtSecondLastName_RSched = new javax.swing.JTextField();
        txtSecondN_RSched = new javax.swing.JTextField();
        lb_errorID_RSh = new javax.swing.JLabel();
        lb_errorPassword_RSh = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lb_errorSecondName_RSh = new javax.swing.JLabel();
        lb_errorFistName_RSh = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lb_errorFistLastName_RSh = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lb_errorSecondLastName_RSh = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbx_SecurityQuestion_RSched = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtRASecurity_RSched = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtNumCedula_RSche1 = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnEye_RProf = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnSave_RSched = new javax.swing.JButton();
        btn_Cancel1_RSched = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        msgRegisterScheduler.setBackground(new java.awt.Color(0, 0, 0));
        msgRegisterScheduler.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        msgRegisterScheduler.setForeground(new java.awt.Color(255, 255, 255));
        msgRegisterScheduler.setText("Registrar agendador");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(msgRegisterScheduler)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(msgRegisterScheduler)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        lb_errorID_RSh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorID_RSh.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorID_RSh.setText("Error");

        lb_errorPassword_RSh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorPassword_RSh.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorPassword_RSh.setText("Error");

        jLabel3.setFont(new java.awt.Font("Cascadia Code", 0, 16)); // NOI18N
        jLabel3.setText("Ingrese los siguientes datos:");

        jLabel4.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel4.setText("Número de cédula:");

        lb_errorSecondName_RSh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorSecondName_RSh.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorSecondName_RSh.setText("Error");

        lb_errorFistName_RSh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorFistName_RSh.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorFistName_RSh.setText("Error");

        jLabel5.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel5.setText("Contraseña:");

        lb_errorFistLastName_RSh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorFistLastName_RSh.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorFistLastName_RSh.setText("Error");

        jLabel6.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel6.setText("Primer nombre:");

        lb_errorSecondLastName_RSh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorSecondLastName_RSh.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorSecondLastName_RSh.setText("Error");

        jLabel7.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel7.setText("Segundo nombre:");

        cbx_SecurityQuestion_RSched.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "¿Cuál es el nombre de tu primera mascota?", "¿Cuál es el nombre de tu primer colegio?", "¿Cuál es el nombre del lugar donde naciste?" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(169, 169, 169));
        jLabel8.setText("(Opcional)");

        jLabel12.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel12.setText("Escoja una pregunta de seguridad");

        jLabel9.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel9.setText("Primer apellido:");

        jLabel14.setFont(new java.awt.Font("Cascadia Code", 0, 13)); // NOI18N
        jLabel14.setText("Respuesta:");

        jLabel10.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel10.setText("Segundo apellido:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(169, 169, 169));
        jLabel11.setText("(Opcional)");

        jLabel13.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel13.setText("Confirme contraseña:");

        btnEye_RProf.setText("eye");
        btnEye_RProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEye_RProfActionPerformed(evt);
            }
        });

        btnRegresar.setText("Regresar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtFirstName_RScheduler, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel7))
                                            .addGap(52, 52, 52))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(107, 107, 107)))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtSecondN_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtFirstLastName_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lb_errorFistName_RSh, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lb_errorSecondName_RSh, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lb_errorFistLastName_RSh, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSecondLastName_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lb_errorSecondLastName_RSh, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(1, 1, 1))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbx_SecurityQuestion_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRASecurity_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(175, 175, 175)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_errorID_RSh, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNumCedula_RSche1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(85, 85, 85)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_errorPassword_RSh, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEye_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(13, 13, 13)
                                .addComponent(txtConfPass_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(65, 65, 65))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegresar)
                        .addGap(19, 19, 19))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnRegresar)))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtNumCedula_RSche1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2)
                                .addComponent(lb_errorID_RSh)
                                .addGap(107, 107, 107))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnEye_RProf))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_errorPassword_RSh)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtConfPass_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(110, 110, 110))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(11, 11, 11)
                        .addComponent(cbx_SecurityQuestion_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtRASecurity_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtFirstName_RScheduler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addComponent(lb_errorFistName_RSh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtSecondN_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lb_errorSecondName_RSh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtFirstLastName_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addComponent(lb_errorFistLastName_RSh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtSecondLastName_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_errorSecondLastName_RSh)
                            .addComponent(jLabel11))))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnSave_RSched.setText("GUARDAR");
        btnSave_RSched.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave_RSchedActionPerformed(evt);
            }
        });

        btn_Cancel1_RSched.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/close.png"))); // NOI18N
        btn_Cancel1_RSched.setText("CANCELAR");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(btn_Cancel1_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Cancel1_RSched, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSave_RSchedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave_RSchedActionPerformed

       if(!validarCampos()) return;
       
       try{
           User u = new User();
           
           u.setCedUser(Integer.parseInt(txtNumCedula_RSche1.getText().trim()));
           u.setPassUser(new String(txtPassword.getPassword()));
           u.setNameUser(txtFirstName_RScheduler.getText().trim());
           u.setSecondNameUser(txtSecondN_RSched.getText().trim());
           u.setLastNameUser(txtFirstLastName_RSched.getText().trim());
           u.setSecondLastNameUser(txtSecondLastName_RSched.getText().trim());
           u.setStatusUser(StatusUserEnum.Active);
           u.setRoleUser(RoleUserEnum.Scheduler);
           u.setSecurityQuestion(cbx_SecurityQuestion_RSched.getSelectedItem().toString());
           u.setSecurityAnswer(txtRASecurity_RSched.getText().trim());
           
           
           boolean ok = iuserservice.regUser(u);
           
            if (ok) {
                JOptionPane.showMessageDialog(this, "Registro exitoso.");
                this.dispose();  // cerrar ventana
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado.");
        }
    }//GEN-LAST:event_btnSave_RSchedActionPerformed

    private void btnEye_RProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEye_RProfActionPerformed
       
         if (mostrarPassword) {
            txtPassword.setEchoChar('•'); 
            mostrarPassword = false;
        } else {
            txtPassword.setEchoChar((char)0);
            mostrarPassword = true;
        } 
    }//GEN-LAST:event_btnEye_RProfActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEye_RProf;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnSave_RSched;
    private javax.swing.JButton btn_Cancel1_RSched;
    private javax.swing.JComboBox<String> cbx_SecurityQuestion_RSched;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lb_errorFistLastName_RSh;
    private javax.swing.JLabel lb_errorFistName_RSh;
    private javax.swing.JLabel lb_errorID_RSh;
    private javax.swing.JLabel lb_errorPassword_RSh;
    private javax.swing.JLabel lb_errorSecondLastName_RSh;
    private javax.swing.JLabel lb_errorSecondName_RSh;
    private javax.swing.JLabel msgRegisterScheduler;
    private javax.swing.JPasswordField txtConfPass_RSched;
    private javax.swing.JTextField txtFirstLastName_RSched;
    private javax.swing.JTextField txtFirstName_RScheduler;
    private javax.swing.JTextField txtNumCedula_RSche1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtRASecurity_RSched;
    private javax.swing.JTextField txtSecondLastName_RSched;
    private javax.swing.JTextField txtSecondN_RSched;
    // End of variables declaration//GEN-END:variables
}
