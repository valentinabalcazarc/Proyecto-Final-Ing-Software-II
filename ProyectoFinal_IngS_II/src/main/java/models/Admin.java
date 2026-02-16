
package models;


public class Admin {
   private String adminUser; 
   private String adminPassword;

    public Admin(String adminUser, String adminPassword) {
        this.adminUser = adminUser;
        this.adminPassword = adminPassword;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
   
   
}
