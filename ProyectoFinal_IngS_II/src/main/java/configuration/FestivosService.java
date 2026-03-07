package configuration;

import com.toedter.calendar.JCalendar;
import java.awt.Color;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;

public class FestivosService {

    private Set<LocalDate> festivos;

    public FestivosService() {

        festivos = new HashSet<>();

        festivos.add(LocalDate.of(2026,3,23 ));
        festivos.add(LocalDate.of(2026,4,2));
        festivos.add(LocalDate.of(2026,4,3 ));
        festivos.add(LocalDate.of(2026,5,1));
        festivos.add(LocalDate.of(2026,5,18));
        festivos.add(LocalDate.of(2026,6,8));
        festivos.add(LocalDate.of(2026,6,15));
        festivos.add(LocalDate.of(2026,6,29));
        festivos.add(LocalDate.of(2026,7,20));
        festivos.add(LocalDate.of(2026,8,7));
        festivos.add(LocalDate.of(2026,8,17));
        festivos.add(LocalDate.of(2026,10,12));
        festivos.add(LocalDate.of(2026,11,2));
        festivos.add(LocalDate.of(2026,11,16));
        festivos.add(LocalDate.of(2026,12,8));
        festivos.add(LocalDate.of(2026,12,25));
        
    }

    public boolean esFestivo(LocalDate fecha){

        return festivos.contains(fecha);
    }

    public void pintarFestivos(JCalendar calendar){

        int año = calendar.getYearChooser().getYear();
        int mes = calendar.getMonthChooser().getMonth() + 1;

        java.awt.Component[] dias =
                calendar.getDayChooser().getDayPanel().getComponents();

        for (java.awt.Component c : dias) {

            if (c instanceof JButton) {

                JButton dia = (JButton) c;

                try {

                    int numero = Integer.parseInt(dia.getText());

                    LocalDate fecha = LocalDate.of(año, mes, numero);

                    if (esFestivo(fecha)) {

                        dia.setBackground(Color.RED);
                        dia.setForeground(Color.WHITE);

                    }

                } catch (Exception e) {}

            }
        }
    }
    
    public void pintarDomingos(JCalendar calendar){

        java.awt.Component[] dias =
                calendar.getDayChooser().getDayPanel().getComponents();

        for (java.awt.Component c : dias) {

            if (c instanceof JButton) {

                JButton dia = (JButton) c;

                int columna = calendar.getDayChooser()
                        .getDayPanel()
                        .getComponentZOrder(dia) % 7;

                if (columna == 6) { // columna del domingo
                    dia.setForeground(Color.GRAY);
                }
            }
        }
    }
    
    
}