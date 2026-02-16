
package models;

public class Professional extends User {
    private double codProf;
    private String genProf;
    private double phoneProf;
    private String statusProf;
    private String typeProf;
    private String SpecialityProf;
    private short attentionInterval;

    public Professional(double codProf, String genProf, double phoneProf, String statusProf, String typeProf, String SpecialityProf, short attentionInterval, int codUser, int cedUser, String passUser, String nameUser, String secondNameUser, String lastNameUser, String secondLastNameUser, String statusUser, String roleUser, String securityQuestion, String securityAnswer) {
        super(codUser, cedUser, passUser, nameUser, secondNameUser, lastNameUser, secondLastNameUser, statusUser, roleUser, securityQuestion, securityAnswer);
        this.codProf = codProf;
        this.genProf = genProf;
        this.phoneProf = phoneProf;
        this.statusProf = statusProf;
        this.typeProf = typeProf;
        this.SpecialityProf = SpecialityProf;
        this.attentionInterval = attentionInterval;
    }

    public double getCodProf() {
        return codProf;
    }

    public void setCodProf(double codProf) {
        this.codProf = codProf;
    }

    public String getGenProf() {
        return genProf;
    }

    public void setGenProf(String genProf) {
        this.genProf = genProf;
    }

    public double getPhoneProf() {
        return phoneProf;
    }

    public void setPhoneProf(double phoneProf) {
        this.phoneProf = phoneProf;
    }

    public String getStatusProf() {
        return statusProf;
    }

    public void setStatusProf(String statusProf) {
        this.statusProf = statusProf;
    }

    public String getTypeProf() {
        return typeProf;
    }

    public void setTypeProf(String typeProf) {
        this.typeProf = typeProf;
    }

    public String getSpecialityProf() {
        return SpecialityProf;
    }

    public void setSpecialityProf(String SpecialityProf) {
        this.SpecialityProf = SpecialityProf;
    }

    public short getAttentionInterval() {
        return attentionInterval;
    }

    public void setAttentionInterval(short attentionInterval) {
        this.attentionInterval = attentionInterval;
    }

    
}