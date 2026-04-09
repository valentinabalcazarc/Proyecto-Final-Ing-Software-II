package filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransformDateStageTest {

    private TransformDateStage filter;

    @BeforeEach
    public void setUp() {
        filter = new TransformDateStage();
    }

    @Test
    public void testFilter_fechaEnero_formatoEspañolCorrecto() {
        String resultado = filter.filter("2026-01-15");
        assertEquals("15-ene-2026", resultado);
    }

    @Test
    public void testFilter_fechaMarzo_formatoEspañolCorrecto() {
        String resultado = filter.filter("2026-03-23");
        assertEquals("23-mar-2026", resultado);
    }

    @Test
    public void testFilter_fechaDiciembre_formatoEspañolCorrecto() {
        String resultado = filter.filter("2026-12-25");
        assertEquals("25-dic-2026", resultado);
    }

    @Test
    public void testFilter_resultadoEnMinusculas() {
        String resultado = filter.filter("2026-06-01");
        assertEquals(resultado, resultado.toLowerCase(),
                "El resultado debe estar en minúsculas");
    }

    @Test
    public void testFilter_formatoConGuiones() {
        String resultado = filter.filter("2026-07-20");
        // Debe tener el formato dd-MMM-yyyy
        assertTrue(resultado.matches("\\d{2}-[a-z]{3}-\\d{4}"),
                "El formato debe ser dd-mes-yyyy con guiones");
    }

    @Test
    public void testFilter_primeroDeMayo() {
        String resultado = filter.filter("2026-05-01");
        assertEquals("01-may-2026", resultado);
    }

    @Test
    public void testFilter_fechaInvalida_lanzaExcepcion() {
        assertThrows(Exception.class, () -> filter.filter("fecha-invalida"));
    }

    @Test
    public void testFilter_fechaAgosto() {
        String resultado = filter.filter("2026-08-07");
        assertEquals("07-ago-2026", resultado);
    }
}
