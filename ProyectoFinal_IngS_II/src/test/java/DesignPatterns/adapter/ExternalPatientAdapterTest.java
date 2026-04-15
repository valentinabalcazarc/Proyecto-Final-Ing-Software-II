package DesignPatterns.adapter;

import models.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExternalPatientAdapterTest {

    //Test 1: JSON completo
    @Test
    void testGetPatientCompleto() {

        ExternalService service = new ExternalService() {
            @Override
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
            }
        };

        ExternalPatientAdapter adapter = new ExternalPatientAdapter(service);
        Patient patient = adapter.getPatient();

        assertNotNull(patient);

        assertEquals(1, patient.getCodPatient());
        assertEquals(123456, patient.getIdPatient());
        assertEquals("Jose", patient.getNamePatient());
        assertEquals("Luis", patient.getSecondNamePatient());
        assertEquals("Lopez", patient.getLastNamePatient());
        assertEquals("Perez", patient.getSecondLastNamePatient());
        assertEquals(300123456, patient.getPhonePatient());
        assertEquals(LocalDate.of(2000, 5, 10), patient.getDateBirthPatient());
        assertEquals("M", patient.getGenderPatient());
    }

    //Test 2: JSON sin fecha
    @Test
    void testSinFecha() {

        ExternalService service = new ExternalService() {
            @Override
            public String getPatientData() {
                return "{"
                        + "\"codPatient\": 2,"
                        + "\"idPatient\": 999,"
                        + "\"namePatient\": \"Ana\""
                        + "}";
            }
        };

        ExternalPatientAdapter adapter = new ExternalPatientAdapter(service);
        Patient patient = adapter.getPatient();

        assertNotNull(patient);

        assertEquals(2, patient.getCodPatient());
        assertEquals(999, patient.getIdPatient());
        assertEquals("Ana", patient.getNamePatient());

        assertNull(patient.getDateBirthPatient());
    }

    // Test 3: Campos faltantes
    @Test
    void testCamposFaltantes() {

        ExternalService service = new ExternalService() {
            @Override
            public String getPatientData() {
                return "{}";
            }
        };

        ExternalPatientAdapter adapter = new ExternalPatientAdapter(service);
        Patient patient = adapter.getPatient();

        assertNotNull(patient);

        assertEquals(0, patient.getCodPatient());
        assertEquals(0, patient.getIdPatient());
        assertEquals("", patient.getNamePatient());
        assertEquals("", patient.getSecondNamePatient());
        assertEquals("", patient.getLastNamePatient());
        assertEquals("", patient.getSecondLastNamePatient());
        assertEquals(0, patient.getPhonePatient());
        assertNull(patient.getDateBirthPatient());
        assertEquals("", patient.getGenderPatient());
    }

    //Test 4: JSON inválido (CORREGIDO)
    @Test
    void testJsonInvalido() {

        ExternalService service = new ExternalService() {
            @Override
            public String getPatientData() {
                return "esto no es json";
            }
        };

        ExternalPatientAdapter adapter = new ExternalPatientAdapter(service);
        Patient patient = adapter.getPatient();

        assertNotNull(patient);

        assertEquals(-1, patient.getIdPatient());
    }
}