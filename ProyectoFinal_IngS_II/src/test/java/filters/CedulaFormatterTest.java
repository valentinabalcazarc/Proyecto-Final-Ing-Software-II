package filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CedulaFormatterTest {

    private CedulaFormatter formatter;

    @BeforeEach
    public void setUp() {
        formatter = new CedulaFormatter();
    }

    @Test
    public void testFilter_cedulaMayorAMil_separaPorPuntos() {
        // 1.000.000 -> "1.000.000"
        String resultado = formatter.filter(1000000);
        assertEquals("1.000.000", resultado);
    }

    @Test
    public void testFilter_cedulaDe4Digitos_seFormateaCorrectamente() {
        String resultado = formatter.filter(1234);
        assertEquals("1.234", resultado);
    }

    @Test
    public void testFilter_cedulaMenorAMil_sinPunto() {
        String resultado = formatter.filter(999);
        assertEquals("999", resultado);
    }

    @Test
    public void testFilter_cedulaExactoMil_conPunto() {
        String resultado = formatter.filter(1000);
        assertEquals("1.000", resultado);
    }

    @Test
    public void testFilter_cedulaTipicaColombiana_formatoCorrecto() {
        // Cédula típica: 1073654321
        String resultado = formatter.filter(1073654321);
        assertEquals("1.073.654.321", resultado);
    }

    @Test
    public void testFilter_cero_devuelveCero() {
        String resultado = formatter.filter(0);
        assertEquals("0", resultado);
    }

    @Test
    public void testFilter_resultadoNoContieneComasSinoSoloPuntos() {
        String resultado = formatter.filter(12345678);
        assertFalse(resultado.contains(","), "No debe contener comas");
        assertTrue(resultado.contains("."), "Debe contener puntos como separadores");
    }
}
