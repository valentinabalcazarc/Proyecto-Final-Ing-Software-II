package views;

import models.Appointment;
import views.Admin.*;
import views.Patient.*;
import views.Professional.*;

public class ViewManager {
    private static ViewManager instance;

    // Vistas Generales
    private final winLogin login;

    // Vistas Admin
    private final winAdmin principalAdmin;
    private final winRegisterProfessional regProf;
    private final winUserRegister regUser;

    // Vistas Patient
    private final winAutomaticRecommendation autoRecomendation;
    private final winPatientInfo_Pat infoPatient_Pat;
    private final winPrincipal_Pat principalPatient;
    private final winSelectService_Pat selectService_Pat;
    private final winSelectSpecificAppointment_Pat selectSpecificApp_Pat;
    private final winViewAppoinment_Pat viewAppointment_Pat;

    // Vistas Professional
    private final winCreateAppointment_Part2_Prof createAppPart2_Prof;
    private final winCreateAppointment_Prof createApp_Prof;
    private final winExport export;
    //private final winExportFormatSelection exportFormat;
    private final winManageAppointment_Prof manageApp_Prof;
    private final winPatientInfo_Prof infoPatient_Prof;
    private final winPrincipal_Prof principalProf;
    private final winViewAppointments_Prof viewApps_Prof;

    // Constructor Privado
    private ViewManager() {
        // Inicialización de todas las ventanas
        this.login = new winLogin();

        // Admin
        this.principalAdmin = new winAdmin();
        this.regProf = new winRegisterProfessional();
        this.regUser = new winUserRegister();

        // Patient
        this.autoRecomendation = new winAutomaticRecommendation();
        this.infoPatient_Pat = new winPatientInfo_Pat();
        this.principalPatient = new winPrincipal_Pat();
        this.selectService_Pat = new winSelectService_Pat();
        this.selectSpecificApp_Pat = new winSelectSpecificAppointment_Pat();
        this.viewAppointment_Pat = new winViewAppoinment_Pat();

        // Professional
        this.createAppPart2_Prof = new winCreateAppointment_Part2_Prof();
        this.createApp_Prof = new winCreateAppointment_Prof();
        this.export = new winExport();
        //this.exportFormat = new winExportFormatSelection();
        this.manageApp_Prof = new winManageAppointment_Prof();
        this.infoPatient_Prof = new winPatientInfo_Prof();
        this.principalProf = new winPrincipal_Prof();
        this.viewApps_Prof = new winViewAppointments_Prof();
    }

    // Singleton Instance
    public static ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }

    // Getters para Vistas Generales
    public winLogin getLogin() { return login; }

    // Getters para Admin
    public winAdmin getPrincipalAdmin() { return principalAdmin; }
    public winRegisterProfessional getRegProf() { return regProf; }
    public winUserRegister getRegUser() { return regUser; }

    // Getters para Patient
    public winAutomaticRecommendation getAutoRecomendation() { return autoRecomendation; }
    public winPatientInfo_Pat getInfoPatient_Pat() { return infoPatient_Pat; }
    public winPrincipal_Pat getPrincipalPatient() { return principalPatient; }
    public winSelectService_Pat getSelectService_Pat() { return selectService_Pat; }
    public winSelectSpecificAppointment_Pat getSelectSpecificApp_Pat() { return selectSpecificApp_Pat; }
    public winViewAppoinment_Pat getViewAppointment_Pat() { return viewAppointment_Pat; }

    // Getters para Professional
    public winCreateAppointment_Part2_Prof getCreateAppPart2_Prof() { return createAppPart2_Prof; }
    public winCreateAppointment_Prof getCreateApp_Prof() { return createApp_Prof; }
    public winExport getExport() { return export; }
    //public winExportFormatSelection getExportFormat() { return exportFormat; }
    public winManageAppointment_Prof getManageApp_Prof() { return manageApp_Prof; }
    public winPatientInfo_Prof getInfoPatient_Prof() { return infoPatient_Prof; }
    public winPrincipal_Prof getPrincipalProf() { return principalProf; }
    public winViewAppointments_Prof getViewApps_Prof() { return viewApps_Prof; }
}