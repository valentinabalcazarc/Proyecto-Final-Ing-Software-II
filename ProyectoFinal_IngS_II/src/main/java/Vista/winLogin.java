package Vista;

import javax.swing.JOptionPane;

import repository.UserRepository;
import repository.UserRepositoryImpl;
import services.UserService;
import services.UserServiceImpl;
import configuration.IAuthenticationService;
import configuration.AuthenticationBCrypt;

public class winLogin extends javax.swing.JFrame {

    private UserService userService;

    public winLogin() {
        initComponents();

        // Inicialización de dependencias
        UserRepository userRepository = new UserRepositoryImpl();
        IAuthenticationService authService = new AuthenticationBCrypt();
        userService = new UserServiceImpl(userRepository, authService);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPass_Login = new javax.swing.JTextField();
        btnInicioSecion = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnRegister_Login = new javax.swing.JButton();
        txtUser_login = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("BIENVENIDO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(150, 150, 150))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        jLabel2.setText("Usuario:");

        jLabel3.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        jLabel3.setText("Contraseña:");

        jLabel4.setFont(new java.awt.Font("Cascadia Code", 1, 14)); // NOI18N
        jLabel4.setText("Ingrese sus credenciales");

        txtPass_Login.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N

        btnInicioSecion.setBackground(new java.awt.Color(102, 102, 255));
        btnInicioSecion.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        btnInicioSecion.setForeground(new java.awt.Color(255, 255, 255));
        btnInicioSecion.setText("Iniciar sesión");
        btnInicioSecion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioSecionActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        jLabel5.setText("¿No tienes cuenta? Registrate");

        btnRegister_Login.setBackground(new java.awt.Color(102, 102, 255));
        btnRegister_Login.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        btnRegister_Login.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister_Login.setText("Registro");
        btnRegister_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegister_LoginActionPerformed(evt);
            }
        });

        txtUser_login.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRegister_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioSecion, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(151, 151, 151))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUser_login, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPass_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel5)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtUser_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtPass_Login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnInicioSecion, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegister_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicioSecionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioSecionActionPerformed

    String cedText = txtUser_login.getText().trim();
    String password = txtPass_Login.getText().trim();

    if (cedText.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe ingresar usuario y contraseña.");
        return;
    }

    int ced;

    try {
        ced = Integer.parseInt(cedText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El usuario debe ser numérico.");
        return;
    }
    
    System.out.println("cedula: "+ ced);
    System.out.println("pass: "+ password);

    boolean autenticado = userService.authUser(ced, password);

    if (autenticado) {
        JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
    } else {
        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
    }

    }//GEN-LAST:event_btnInicioSecionActionPerformed

    private void btnRegister_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegister_LoginActionPerformed

        winRegisterProfessional winProfessional = new winRegisterProfessional();
        
        winProfessional.setVisible(true);
        
        this.dispose();


    }//GEN-LAST:event_btnRegister_LoginActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInicioSecion;
    private javax.swing.JButton btnRegister_Login;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtPass_Login;
    private javax.swing.JTextField txtUser_login;
    // End of variables declaration//GEN-END:variables
}
