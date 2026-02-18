package Vista;

import javax.swing.JOptionPane;
import models.StatusUserEnum;
import models.TypeProfEnum;
import models.User;
import models.RoleUserEnum;
import services.UserService;

public class winUserRegister extends javax.swing.JFrame {
    
    UserService iuserservice; 
    private boolean mostrarPassword = false;
    
    public winUserRegister(UserService iuserservice) {
        initComponents();
        this.iuserservice=iuserservice;
        lb_errorID.setVisible(false);
        lb_errorFistName.setVisible(false);
        lb_errorSecondName.setVisible(false);
        lb_errorFistLastName.setVisible(false);
        lb_errorSecondLastName.setVisible(false);
        lb_errorPassword.setVisible(false);
    }

    private boolean validarCampos() {

        // validacion de vacios importantes
        if (tF_userID.getText().trim().isEmpty() ||
            pF_password.getPassword().length == 0 ||
            pF_password2.getPassword().length == 0 ||
            tF_userFirstName.getText().trim().isEmpty() ||
            tF_userFirstLastName.getText().trim().isEmpty() ||
            tF_SecurityQuestion.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Debe llenar al menos: Cédula, Contraseña, Primer Nombre y Primer Apellido.");
            return false;
        }

        // validación de cedula numérica
        try {
            Long.valueOf(tF_userID.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cédula debe contener solo números.");
            return false;
        }

        // validar cadenas sin numeros
        if (!esNombreValido(tF_userFirstName.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El primer nombre contiene caracteres inválidos.");
            return false;
        }

        if (!tF_userSecondName.getText().trim().isEmpty() &&
            !esNombreValido(tF_userSecondName.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El segundo nombre contiene caracteres inválidos.");
            return false;
        }

        if (!esNombreValido(tF_userFirstLastName.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El primer apellido contiene caracteres inválidos.");
            return false;
        }

        if (!tF_userSecondLastName.getText().trim().isEmpty() &&
            !esNombreValido(tF_userSecondLastName.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El segundo apellido contiene caracteres inválidos.");
            return false;
        }
        
        if (!tF_SecurityQuestion.getText().trim().isEmpty() &&
            !esNombreValido(tF_SecurityQuestion.getText().trim())) {
            JOptionPane.showMessageDialog(this, "La respuesta de seguridad contiene caracteres inválidos.");
            return false;
        }
        
        if (!java.util.Arrays.equals(pF_password.getPassword(), pF_password2.getPassword())) {
            JOptionPane.showMessageDialog(this, "La contraseña no concuerda con su confirmación.");
            return false;
        }
        
        if (!validarPasswordSegura(pF_password.getPassword())) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener:\n" +
                    "- Mínimo 6 caracteres\n" +
                    "- Al menos una mayúscula\n" +
                    "- Al menos un número\n" +
                    "- Al menos un carácter especial");
            return false;
        }

        return true;
    }

    
    private boolean esNombreValido(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    }
    
    private boolean validarPasswordSegura(char[] passwordChars) {
        String password = new String(passwordChars);

        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-]).{6,}$";
        return password.matches(regex);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tF_userID = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        pF_password = new javax.swing.JPasswordField();
        jb_Eye = new javax.swing.JButton();
        pF_password2 = new javax.swing.JPasswordField();
        tF_userFirstName = new javax.swing.JTextField();
        tF_userFirstLastName = new javax.swing.JTextField();
        tF_userSecondLastName = new javax.swing.JTextField();
        tF_userSecondName = new javax.swing.JTextField();
        btn_Save = new javax.swing.JButton();
        btn_Cancel = new javax.swing.JButton();
        lb_errorID = new javax.swing.JLabel();
        lb_errorPassword = new javax.swing.JLabel();
        lb_errorSecondName = new javax.swing.JLabel();
        lb_errorFistName = new javax.swing.JLabel();
        lb_errorFistLastName = new javax.swing.JLabel();
        lb_errorSecondLastName = new javax.swing.JLabel();
        cbx_SecurityQuestion = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tF_SecurityQuestion = new javax.swing.JTextField();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PIEDRA AZUL");

        jLabel2.setFont(new java.awt.Font("Cascadia Code", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Registro de Usuario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(287, 287, 287)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Cascadia Code", 0, 16)); // NOI18N
        jLabel3.setText("Ingrese los siguientes datos:");

        jLabel4.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel4.setText("Número de cédula:");

        jLabel5.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel5.setText("Contraseña:");

        jLabel6.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel6.setText("Primer nombre:");

        jLabel7.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel7.setText("Segundo nombre:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(169, 169, 169));
        jLabel8.setText("(Opcional)");

        jLabel9.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel9.setText("Primer apellido:");

        jLabel10.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel10.setText("Segundo apellido:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(169, 169, 169));
        jLabel11.setText("(Opcional)");

        jLabel13.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel13.setText("Confirme contraseña:");

        jb_Eye.setText("eye");
        jb_Eye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_EyeActionPerformed(evt);
            }
        });

        btn_Save.setText("GUARDAR");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        btn_Cancel.setText("CANCELAR");
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelActionPerformed(evt);
            }
        });

        lb_errorID.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorID.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorID.setText("Error");

        lb_errorPassword.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorPassword.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorPassword.setText("Error");

        lb_errorSecondName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorSecondName.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorSecondName.setText("Error");

        lb_errorFistName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorFistName.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorFistName.setText("Error");

        lb_errorFistLastName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorFistLastName.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorFistLastName.setText("Error");

        lb_errorSecondLastName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorSecondLastName.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorSecondLastName.setText("Error");

        cbx_SecurityQuestion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "¿Cuál es el nombre de tu primera mascota?", "¿Cuál es el nombre de tu primer colegio?", "¿Cuál es el nombre del lugar donde naciste?" }));

        jLabel12.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel12.setText("Escoja una pregunta de seguridad");

        jLabel14.setFont(new java.awt.Font("Cascadia Code", 0, 13)); // NOI18N
        jLabel14.setText("Respuesta:");

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tF_userSecondName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tF_userFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_errorSecondName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_errorFistName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(tF_userSecondLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tF_userFirstLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lb_errorSecondLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_errorFistLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(btn_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbx_SecurityQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tF_SecurityQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(69, 69, 69))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_errorID, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tF_userID, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_errorPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(pF_password, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jb_Eye, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pF_password2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(43, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegresar)
                        .addGap(14, 14, 14))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnRegresar))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel7)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(tF_userFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(lb_errorFistName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tF_userSecondName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(lb_errorSecondName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tF_userFirstLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(lb_errorFistLastName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tF_userSecondLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_errorSecondLastName))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(tF_userID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(lb_errorID)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(43, 43, 43)
                                .addComponent(jLabel13))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pF_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jb_Eye))
                                .addGap(2, 2, 2)
                                .addComponent(lb_errorPassword)
                                .addGap(18, 18, 18)
                                .addComponent(pF_password2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbx_SecurityQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(tF_SecurityQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        if (!validarCampos()) return;

        try{
            User u = new User();

            u.setCedUser(Integer.parseInt(tF_userID.getText().trim()));
            u.setPassUser(new String(pF_password.getPassword()));
            u.setNameUser(tF_userFirstName.getText().trim());
            u.setSecondNameUser(tF_userSecondName.getText().trim());
            u.setLastNameUser(tF_userFirstLastName.getText().trim());
            u.setSecondLastNameUser(tF_userSecondLastName.getText().trim());
            u.setStatusUser(StatusUserEnum.Active);
            u.setRoleUser(RoleUserEnum.Patient);
            u.setSecurityQuestion(cbx_SecurityQuestion.getSelectedItem().toString());
            u.setSecurityAnswer(tF_SecurityQuestion.getText().trim());

            boolean ok = iuserservice.regUser(u);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Registro exitoso.");
                this.dispose();  
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar.");
            }

        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado.");
        }
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void jb_EyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_EyeActionPerformed
        if (mostrarPassword) {
            pF_password.setEchoChar('•'); 
            mostrarPassword = false;
        } else {
            pF_password.setEchoChar((char)0);
            mostrarPassword = true;
        }  
    }//GEN-LAST:event_jb_EyeActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_CancelActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btn_Cancel;
    private javax.swing.JButton btn_Save;
    private javax.swing.JComboBox<String> cbx_SecurityQuestion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jb_Eye;
    private javax.swing.JLabel lb_errorFistLastName;
    private javax.swing.JLabel lb_errorFistName;
    private javax.swing.JLabel lb_errorID;
    private javax.swing.JLabel lb_errorPassword;
    private javax.swing.JLabel lb_errorSecondLastName;
    private javax.swing.JLabel lb_errorSecondName;
    private javax.swing.JPasswordField pF_password;
    private javax.swing.JPasswordField pF_password2;
    private javax.swing.JTextField tF_SecurityQuestion;
    private javax.swing.JTextField tF_userFirstLastName;
    private javax.swing.JTextField tF_userFirstName;
    private javax.swing.JTextField tF_userID;
    private javax.swing.JTextField tF_userSecondLastName;
    private javax.swing.JTextField tF_userSecondName;
    // End of variables declaration//GEN-END:variables
}
