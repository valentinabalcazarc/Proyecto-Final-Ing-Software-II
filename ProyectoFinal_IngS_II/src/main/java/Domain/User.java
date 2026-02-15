
package Domain;


public class User {
    protected double codUser;
    protected double cedUser;
    protected double passUser;
    protected String  nameUser;
    protected String lastName;
    protected String statusUser;
    protected String typeUser;

    public User(double codUser, double cedUser, double passUser, String nameUser, String lastName, String statusUser, String typeUser) {
        this.codUser = codUser;
        this.cedUser = cedUser;
        this.passUser = passUser;
        this.nameUser = nameUser;
        this.lastName = lastName;
        this.statusUser = statusUser;
        this.typeUser = typeUser;
    }

    public double getCodUser() {
        return codUser;
    }

    public void setCodUser(double codUser) {
        this.codUser = codUser;
    }

    public double getCedUser() {
        return cedUser;
    }

    public void setCedUser(double cedUser) {
        this.cedUser = cedUser;
    }

    public double getPassUser() {
        return passUser;
    }

    public void setPassUser(double passUser) {
        this.passUser = passUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
