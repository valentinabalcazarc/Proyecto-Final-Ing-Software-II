
package models;

public class Professional extends User {
    private double codProf;
    private String genProf;
    private double phoneProf;
    private StatusUserEnum statusProf;
    private TypeProfEnum typeProf;
    private SpecialityProfEnum SpecialityProf;
    private short attentionInterval;

    public Professional(double codProf, String genProf, double phoneProf, StatusUserEnum statusProf, TypeProfEnum typeProf, SpecialityProfEnum SpecialityProf, short attentionInterval, int codUser, int cedUser, String passUser, String nameUser, String secondNameUser, String lastNameUser, String secondLastNameUser, StatusUserEnum statusUser, RoleUserEnum roleUser, String securityQuestion, String securityAnswer) {
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

    public Professional(int codUser, int cedUser, String passUser, String nameUser, String secondNameUser, String lastNameUser, String secondLastNameUser, StatusUserEnum statusUser, RoleUserEnum roleUser, String securityQuestion, String securityAnswer) {
        super(codUser, cedUser, passUser, nameUser, secondNameUser, lastNameUser, secondLastNameUser, statusUser, roleUser, securityQuestion, securityAnswer);
    }

    public Professional() {
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

    public StatusUserEnum getStatusProf() {
        return statusProf;
    }

    public void setStatusProf(StatusUserEnum statusProf) {
        this.statusProf = statusProf;
    }

    public TypeProfEnum getTypeProf() {
        return typeProf;
    }

    public void setTypeProf(TypeProfEnum typeProf) {
        this.typeProf = typeProf;
    }

    public SpecialityProfEnum getSpecialityProf() {
        return SpecialityProf;
    }

    public void setSpecialityProf(SpecialityProfEnum SpecialityProf) {
        this.SpecialityProf = SpecialityProf;
    }

    public short getAttentionInterval() {
        return attentionInterval;
    }

    public void setAttentionInterval(short attentionInterval) {
        this.attentionInterval = attentionInterval;
    }

    
    
}