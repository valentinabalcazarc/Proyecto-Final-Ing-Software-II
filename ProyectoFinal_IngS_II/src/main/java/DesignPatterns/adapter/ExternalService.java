package DesignPatterns.adapter;

public class ExternalService {

    //PRUEBA 1
    /*
    public String getPatientData() {
        return "{"
                + "\"codPatient\": 1,"
                + "\"idPatient\": 123456,"
                + "\"namePatient\": \"Jose\","
                + "\"secondNamePatient\": \"Luis\","
                + "\"lastNamePatient\": \"Lopez\","
                + "\"secondLastNamePatient\": \"Perez\","
                + "\"phonePatient\": 300123456,"
                + "\"dateBirthPatient\": \"2000-05-10\","
                + "\"genderPatient\": \"M\""
                + "}";
    }*/

    //PRUEBA 2:
    public String getPatientData() {
    return "{"
            + "\"namePatient\":\"Jose\","
            + "\"lastNamePatient\":\"Lopez\""
            + "}";
}
}