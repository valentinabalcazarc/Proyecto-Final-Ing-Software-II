
package Vista;

import javax.swing.JOptionPane;
import models.Professional;
import models.SpecialityProfEnum;
import models.StatusUserEnum;
import models.TypeProfEnum;
import models.User;
import models.roleUserEnum;
import services.IUserService;

public class winRegisterProfessional extends javax.swing.JFrame {

    IUserService iuserservice; 
    
    public winRegisterProfessional() {
        initComponents();
    }
    
    private boolean validarCampos(){
        // validacion de vacios importantes
        if (txtNumCedula.getText().trim().isEmpty() ||
            txtPassword.getPassword().length == 0 ||
            txtConPassword.getPassword().length == 0 ||
            txtFirstNamame.getText().trim().isEmpty() ||
            txtFirstLastName.getText().trim().isEmpty() ||
            txt_SecurityQuestion.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Debe llenar al menos: Cédula, Contraseña, Primer Nombre y Primer Apellido.");
            return false;
        }

        // validación de cedula numérica
        try {
            Long.valueOf(txtNumCedula.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cédula debe contener solo números.");
            return false;
        }

        // validar cadenas sin numeros
        if (!esNombreValido(txtFirstNamame.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El primer nombre contiene caracteres inválidos.");
            return false;
        }

        if (!txtSecondName.getText().trim().isEmpty() &&
            !esNombreValido(txtSecondName.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El segundo nombre contiene caracteres inválidos.");
            return false;
        }

        if (!esNombreValido(txtFirstLastName.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El primer apellido contiene caracteres inválidos.");
            return false;
        }

        if (!txtSecondLastName.getText().trim().isEmpty() &&
            !esNombreValido(txtSecondLastName.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El segundo apellido contiene caracteres inválidos.");
            return false;
        }
        
        if (!txt_SecurityQuestion.getText().trim().isEmpty() &&
            !esNombreValido(txt_SecurityQuestion.getText().trim())) {
            JOptionPane.showMessageDialog(this, "La respuesta de seguridad contiene caracteres inválidos.");
            return false;
        }
        
        if(!(txtPassword.equals(txtConPassword))){
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
        btnEye_RProf = new javax.swing.JButton();
        txtConPassword = new javax.swing.JPasswordField();
        txtFirstNamame = new javax.swing.JTextField();
        txtFirstLastName = new javax.swing.JTextField();
        txtSecondLastName = new javax.swing.JTextField();
        txtSecondName = new javax.swing.JTextField();
        lb_errorID = new javax.swing.JLabel();
        lb_errorPassword_RProf = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lb_errorSecondName_RProf = new javax.swing.JLabel();
        lb_errorFistName_RProf = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lb_errorFistLastName_RProf = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lb_errorSecondLastName_RProf = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbx_SecurityQuestion_RProf = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_SecurityQuestion = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNumCedula = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel15 = new javax.swing.JLabel();
        cbxTipoProf = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cbxEspecialidad = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtPhoneNumber = new javax.swing.JTextField();
        lb_ErrorPhoneNumber = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn_Cancel_RProf = new javax.swing.JButton();
        btn_Save_RProf = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnEye_RProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/eye.png"))); // NOI18N

        lb_errorID.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorID.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorID.setText("Error");

        lb_errorPassword_RProf.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorPassword_RProf.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorPassword_RProf.setText("Error");

        jLabel3.setFont(new java.awt.Font("Cascadia Code", 0, 16)); // NOI18N
        jLabel3.setText("Ingrese los siguientes datos:");

        jLabel4.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel4.setText("Número de cédula:");

        lb_errorSecondName_RProf.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorSecondName_RProf.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorSecondName_RProf.setText("Error");

        lb_errorFistName_RProf.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorFistName_RProf.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorFistName_RProf.setText("Error");

        jLabel5.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel5.setText("Contraseña:");

        lb_errorFistLastName_RProf.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorFistLastName_RProf.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorFistLastName_RProf.setText("Error");

        jLabel6.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel6.setText("Primer nombre:");

        lb_errorSecondLastName_RProf.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_errorSecondLastName_RProf.setForeground(new java.awt.Color(255, 0, 0));
        lb_errorSecondLastName_RProf.setText("Error");

        jLabel7.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel7.setText("Segundo nombre:");

        cbx_SecurityQuestion_RProf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "¿Cuál es el nombre de tu primera mascota?", "¿Cuál es el nombre de tu primer colegio?", "¿Cuál es el nombre del lugar donde naciste?" }));

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

        jLabel15.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel15.setText("Tipo de profeción");

        cbxTipoProf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Doctor", "Terapeuta" }));

        jLabel16.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel16.setText("Especialidad");

        cbxEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Terapia neural", "Quiropraxia", "Fisioterapia" }));

        jLabel17.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel17.setText("Número de télefono: ");

        lb_ErrorPhoneNumber.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lb_ErrorPhoneNumber.setForeground(new java.awt.Color(255, 0, 0));
        lb_ErrorPhoneNumber.setText("Error");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbxEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbx_SecurityQuestion_RProf, 0, 398, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_SecurityQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxTipoProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSecondName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFirstNamame, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtConPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_errorSecondName_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_errorFistName_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lb_errorID, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNumCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lb_errorPassword_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnEye_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtFirstLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_errorFistLastName_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSecondLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_errorSecondLastName_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_ErrorPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(33, 33, 33))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNumCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(lb_errorID)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEye_RProf))
                .addGap(1, 1, 1)
                .addComponent(lb_errorPassword_RProf)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtConPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtFirstNamame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(lb_errorFistName_RProf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtSecondName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lb_errorSecondName_RProf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtFirstLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(lb_errorFistLastName_RProf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSecondLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lb_errorSecondLastName_RProf))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_ErrorPhoneNumber)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbx_SecurityQuestion_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_SecurityQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxTipoProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btn_Cancel_RProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/close.png"))); // NOI18N
        btn_Cancel_RProf.setText("CANCELAR");

        btn_Save_RProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/save.png"))); // NOI18N
        btn_Save_RProf.setText("GUARDAR");
        btn_Save_RProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Save_RProfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Cancel_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(btn_Save_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Cancel_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Save_RProf, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Registrar profesional");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(111, 111, 111))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Save_RProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Save_RProfActionPerformed
        if(!validarCampos()) return;
        
        try{
            
            Professional p = new Professional();
            
            p.setCedUser(Integer.parseInt(txtNumCedula.getText().trim()));
            p.setPassUser(new String(txtPassword.getPassword()));
            p.setNameUser(txtFirstNamame.getText().trim());
            p.setSecondNameUser(txtSecondName.getText().trim());
            p.setLastNameUser(txtFirstLastName.getText().trim());
            p.setSecondLastNameUser(txtSecondLastName.getText().trim());
            p.setPhoneProf(Double.parseDouble(txtPhoneNumber.getText().trim()));
            p.setStatusUser(StatusUserEnum.Active);
            p.setRoleUser(roleUserEnum.Doctor);
            p.setSecurityQuestion(cbx_SecurityQuestion_RProf.getSelectedItem().toString());
            p.setSecurityAnswer(txt_SecurityQuestion.getText().trim());
            
            String seleccionTipoProf = cbxTipoProf.getSelectedItem().toString();
                if (seleccionTipoProf.equals("Doctor")) {
                    p.setTypeProf(TypeProfEnum.DOCTOR);
                } else if (seleccionTipoProf.equals("Terapeuta")) {
                    p.setTypeProf(TypeProfEnum.THERAPIST);
                }
            String seleccionEspecProf = cbxEspecialidad.getSelectedItem().toString();
            
                if (seleccionEspecProf.equals("Terapia neural")) {
                    p.setSpecialityProf(SpecialityProfEnum.Neural_Therapy);
                } else if (seleccionEspecProf.equals("Quiropraxia")) {
                    p.setSpecialityProf(SpecialityProfEnum.Chiropractor);
                } else if (seleccionEspecProf.equals("Fisioterapia")){
                    p.setSpecialityProf(SpecialityProfEnum.Pysiotherapy);
                }
                
                boolean ok = iuserservice.regUser(p);
                
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Registro exitoso.");
                    this.dispose();  // cerrar ventana
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar.");
                }
        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado.");
        }
    }//GEN-LAST:event_btn_Save_RProfActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEye_RProf;
    private javax.swing.JButton btn_Cancel_RProf;
    private javax.swing.JButton btn_Save_RProf;
    private javax.swing.JComboBox<String> cbxEspecialidad;
    private javax.swing.JComboBox<String> cbxTipoProf;
    private javax.swing.JComboBox<String> cbx_SecurityQuestion_RProf;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JLabel lb_ErrorPhoneNumber;
    private javax.swing.JLabel lb_errorFistLastName_RProf;
    private javax.swing.JLabel lb_errorFistName_RProf;
    private javax.swing.JLabel lb_errorID;
    private javax.swing.JLabel lb_errorPassword_RProf;
    private javax.swing.JLabel lb_errorSecondLastName_RProf;
    private javax.swing.JLabel lb_errorSecondName_RProf;
    private javax.swing.JPasswordField txtConPassword;
    private javax.swing.JTextField txtFirstLastName;
    private javax.swing.JTextField txtFirstNamame;
    private javax.swing.JTextField txtNumCedula;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextField txtSecondLastName;
    private javax.swing.JTextField txtSecondName;
    private javax.swing.JTextField txt_SecurityQuestion;
    // End of variables declaration//GEN-END:variables
}
