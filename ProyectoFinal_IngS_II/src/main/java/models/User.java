
package models;


public class User {

    private int codUser;
    private int cedUser;
    private String passUser;
    private String nameUser;
    private String secondNameUser;
    private String lastNameUser;
    private String secondLastNameUser;
    private String statusUser;
    private String typeUser;

    public User(int codUser, int cedUser, String passUser, String nameUser, String secondNameUser, String lastNameUser, String secondLastNameUser, String statusUser, String typeUser) {
        this.codUser = codUser;
        this.cedUser = cedUser;
        this.passUser = passUser;
        this.nameUser = nameUser;
        this.secondNameUser = secondNameUser;
        this.lastNameUser = lastNameUser;
        this.secondLastNameUser = secondLastNameUser;
        this.statusUser = statusUser;
        this.typeUser = typeUser;
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

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }
    
    
    
}
