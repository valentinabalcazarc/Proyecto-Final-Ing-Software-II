package models;

import enums.RoleUserEnum;
import enums.SpecialityProfEnum;
import enums.StatusUserEnum;
import enums.TypeProfEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfessionalTest {

    private Professional professional;

    @BeforeEach
    public void setUp() {
        professional = new Professional();
    }

    @Test
    public void testConstructorVacio_noCreaExcepcion() {
        assertNotNull(professional);
    }

    @Test
    public void testConstructorCompleto_asignaCamposPropios() {
        Professional p = new Professional(
                101.0, "M", 3001234567.0,
                StatusUserEnum.Active, TypeProfEnum.Doctor, SpecialityProfEnum.Physiotherapy, (short) 30,
                1, 87654321, "hash123", "Pedro", "Luis",
                "Martínez", "Soto", StatusUserEnum.Active, RoleUserEnum.Professional,
                "¿Ciudad natal?", "Bogotá"
        );

        assertEquals(101.0, p.getCodProf());
        assertEquals("M", p.getGenProf());
        assertEquals(3001234567.0, p.getPhoneProf());
        assertEquals(StatusUserEnum.Active, p.getStatusProf());
        assertEquals(TypeProfEnum.Doctor, p.getTypeProf());
        assertEquals(SpecialityProfEnum.Physiotherapy, p.getSpecialityProf());
        assertEquals((short) 30, p.getAttentionInterval());
    }

    @Test
    public void testConstructorHerencia_camposUserDisponibles() {
        Professional p = new Professional(
                1, 12345678, "pass", "Laura", "Sofia",
                "Ríos", "Castro", StatusUserEnum.Active, RoleUserEnum.Professional,
                "¿Mascota?", "Max"
        );
        assertEquals("Laura", p.getNameUser());
        assertEquals(RoleUserEnum.Professional, p.getRoleUser());
    }

    @Test
    public void testSetCodProf_asignaCodigo() {
        professional.setCodProf(200.0);
        assertEquals(200.0, professional.getCodProf());
    }

    @Test
    public void testSetGenProf_asignaGenero() {
        professional.setGenProf("F");
        assertEquals("F", professional.getGenProf());
    }

    @Test
    public void testSetPhoneProf_asignaTelefono() {
        professional.setPhoneProf(3159876543.0);
        assertEquals(3159876543.0, professional.getPhoneProf());
    }

    @Test
    public void testSetStatusProf_asignaEstado() {
        professional.setStatusProf(StatusUserEnum.Inactive);
        assertEquals(StatusUserEnum.Inactive, professional.getStatusProf());
    }

    @Test
    public void testSetTypeProf_asignaTipo() {
        professional.setTypeProf(TypeProfEnum.Therapist);
        assertEquals(TypeProfEnum.Therapist, professional.getTypeProf());
    }

    @Test
    public void testSetSpecialityProf_asignaEspecialidad() {
        professional.setSpecialityProf(SpecialityProfEnum.Neural_Therapy);
        assertEquals(SpecialityProfEnum.Neural_Therapy, professional.getSpecialityProf());
    }

    @Test
    public void testSetAttentionInterval_asignaIntervalo() {
        professional.setAttentionInterval((short) 45);
        assertEquals((short) 45, professional.getAttentionInterval());
    }

    @Test
    public void testToString_retornaNombreYApellido() {
        professional.setNameUser("Carlos");
        professional.setLastNameUser("Ruiz");
        assertEquals("Carlos Ruiz", professional.toString());
    }

    @Test
    public void testToString_conNombreVacio_noLanzaExcepcion() {
        professional.setNameUser("");
        professional.setLastNameUser("");
        assertNotNull(professional.toString());
    }

    @Test
    public void testEspecialidadChiropractor_seAsignaCorrectamente() {
        professional.setSpecialityProf(SpecialityProfEnum.Chiropractor);
        assertEquals(SpecialityProfEnum.Chiropractor, professional.getSpecialityProf());
    }
}
