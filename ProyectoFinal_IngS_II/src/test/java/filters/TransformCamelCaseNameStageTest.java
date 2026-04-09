package filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransformCamelCaseNameStageTest {

    private TransformCamelCaseNameStage filter;

    @BeforeEach
    public void setUp() {
        filter = new TransformCamelCaseNameStage();
    }

    @Test
    public void testFilter_nombreEnMinusculas_capitalizaPrimerLetra() {
        assertEquals("Juan", filter.filter("juan"));
    }

    @Test
    public void testFilter_nombreEnMayusculas_convierteACapitalized() {
        assertEquals("Juan", filter.filter("JUAN"));
    }

    @Test
    public void testFilter_nombreCompuesto_capitalizaCadaPalabra() {
        assertEquals("Juan Carlos", filter.filter("juan carlos"));
    }

    @Test
    public void testFilter_nombreCompleto_capitalizaTodo() {
        assertEquals("Maria Del Pilar Rodriguez", filter.filter("maria del pilar rodriguez"));
    }

    @Test
    public void testFilter_mezclaDeMayusculasYMinusculas_normalizaCorrectamente() {
        assertEquals("Carlos Alberto", filter.filter("cArLoS aLbErTo"));
    }

    @Test
    public void testFilter_unaSolaPalabra_capitalizaCorrectamente() {
        assertEquals("Sofia", filter.filter("sofia"));
    }

    @Test
    public void testFilter_resultadoNoTieneEspacioAlFinal() {
        String resultado = filter.filter("juan");
        assertFalse(resultado.endsWith(" "), "El resultado no debe terminar con espacio");
    }

    @Test
    public void testFilter_tresNombres_capitalizaLosThree() {
        assertEquals("Ana Maria Jose", filter.filter("ana maria jose"));
    }
}
