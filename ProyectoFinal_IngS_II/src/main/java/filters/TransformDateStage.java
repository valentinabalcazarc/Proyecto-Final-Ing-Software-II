package filters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TransformDateStage implements IFilter<String, String>{

    @Override
    public String filter(String inp) {
        
        LocalDate fecha = LocalDate.parse(inp);
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", new Locale("es","ES"));

        return fecha.format(formatter).toLowerCase();
    
    }
}
