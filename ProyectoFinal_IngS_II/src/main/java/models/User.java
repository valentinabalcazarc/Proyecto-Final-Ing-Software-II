
package models;


public class User {

    private int codUser;
    private int cedUser;
    private String passUser;
    private String nameUser;
    private String secondNameUser;
    private String lastNameUser;
    private String secondLastNameUser;
    private StatusUserEnum statusUser;
    private roleUserEnum roleUser;
    private String securityQuestion;
    private String securityAnswer;

    public User(int codUser, int cedUser, String passUser, String nameUser, String secondNameUser, String lastNameUser, String secondLastNameUser, StatusUserEnum statusUser, roleUserEnum roleUser, String securityQuestion, String securityAnswer) {
        this.codUser = codUser;
        this.cedUser = cedUser;
        this.passUser = passUser;
        this.nameUser = nameUser;
        this.secondNameUser = secondNameUser;
        this.lastNameUser = lastNameUser;
        this.secondLastNameUser = secondLastNameUser;
        this.statusUser = statusUser;
        this.roleUser = roleUser;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public User() {
    }

    
    
    public int getCodUser() {
        return codUser;
    }

    public void setCodUser(int codUser) {
        this.codUser = codUser;
    }

    public int getCedUser() {
        return cedUser;
    }

    public void setCedUser(int cedUser) {
        this.cedUser = cedUser;
    }

    public String getPassUser() {
        return passUser;
    }

    public void setPassUser(String passUser) {
        this.passUser = passUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getSecondNameUser() {
        return secondNameUser;
    }

    public void setSecondNameUser(String secondNameUser) {
        this.secondNameUser = secondNameUser;
    }

    public String getLastNameUser() {
        return lastNameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
    }

    public String getSecondLastNameUser() {
        return secondLastNameUser;
    }

    public void setSecondLastNameUser(String secondLastNameUser) {
        this.secondLastNameUser = secondLastNameUser;
    }

    public StatusUserEnum getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(StatusUserEnum statusUser) {
        this.statusUser = statusUser;
    }

    public roleUserEnum getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(roleUserEnum roleUser) {
        this.roleUser = roleUser;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

}