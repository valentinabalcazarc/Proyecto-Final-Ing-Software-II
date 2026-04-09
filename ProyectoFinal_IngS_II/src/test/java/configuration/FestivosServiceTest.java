package configuration;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FestivosServiceTest {

    private FestivosService festivosService;

    @BeforeEach
    public void setUp() {
        festivosService = new FestivosService();
    }

    // ── esFestivo ──────────────────────────────────────────────────────────

    @Test
    public void testEsFestivo_primeroDeMayo_esTrue() {
        assertTrue(festivosService.esFestivo(LocalDate.of(2026, 5, 1)));
    }

    @Test
    public void testEsFestivo_veinteDejulio_esTrue() {
        assertTrue(festivosService.esFestivo(LocalDate.of(2026, 7, 20)));
    }

    @Test
    public void testEsFestivo_navidad_esTrue() {
        assertTrue(festivosService.esFestivo(LocalDate.of(2026, 12, 25)));
    }

    @Test
    public void testEsFestivo_inmaculadaConcepciona_esTrue() {
        assertTrue(festivosService.esFestivo(LocalDate.of(2026, 12, 8)));
    }

    @Test
    public void testEsFestivo_diaLaboral_esFalse() {
        // Martes 12 de mayo de 2026 — día laboral normal
        assertFalse(festivosService.esFestivo(LocalDate.of(2026, 5, 12)));
    }

    @Test
    public void testEsFestivo_fechaDelAnioSiguiente_esFalse() {
        assertFalse(festivosService.esFestivo(LocalDate.of(2027, 1, 1)));
    }

    @Test
    public void testEsFestivo_semanasSanta_viernesSanto_esTrue() {
        assertTrue(festivosService.esFestivo(LocalDate.of(2026, 4, 3)));
    }

    @Test
    public void testEsFestivo_juevesJubilar_esTrue() {
        assertTrue(festivosService.esFestivo(LocalDate.of(2026, 4, 2)));
    }

    // ── esDiaInvalido ──────────────────────────────────────────────────────

    @Test
    public void testEsDiaInvalido_sabado_esTrue() {
        // Sábado cualquiera
        LocalDate sabado = LocalDate.of(2026, 5, 2); // sábado
        assertTrue(festivosService.esDiaInvalido(sabado));
    }

    @Test
    public void testEsDiaInvalido_domingo_esTrue() {
        LocalDate domingo = LocalDate.of(2026, 5, 3); // domingo
        assertTrue(festivosService.esDiaInvalido(domingo));
    }

    @Test
    public void testEsDiaInvalido_festivo_esTrue() {
        assertTrue(festivosService.esDiaInvalido(LocalDate.of(2026, 7, 20)));
    }

    @Test
    public void testEsDiaInvalido_lunesLaboral_esFalse() {
        // Lunes 4 de mayo de 2026 — no festivo, no fin de semana
        assertFalse(festivosService.esDiaInvalido(LocalDate.of(2026, 5, 4)));
    }

    @Test
    public void testEsDiaInvalido_miercolesLaboral_esFalse() {
        assertFalse(festivosService.esDiaInvalido(LocalDate.of(2026, 5, 6)));
    }

    @Test
    public void testEsDiaInvalido_viernesLaboral_esFalse() {
        // Viernes 8 de mayo 2026 — es día laboral normal
        assertFalse(festivosService.esDiaInvalido(LocalDate.of(2026, 5, 8)));
    }

    @Test
    public void testEsFestivo_sieteDEAgosto_esTrue() {
        assertTrue(festivosService.esFestivo(LocalDate.of(2026, 8, 7)));
    }

    @Test
    public void testEsFestivo_doceDeOctubre_esTrue() {
        assertTrue(festivosService.esFestivo(LocalDate.of(2026, 10, 12)));
    }
}
