package filters;

import java.text.DecimalFormat;

public class CedulaFormatter implements IFilter<Integer, String>{

    @Override
    public String filter(Integer inp) {

        DecimalFormat formato = new DecimalFormat("#,###");
        String cedulaFormateada = formato.format(inp);

        cedulaFormateada = cedulaFormateada.replace(",", ".");

        return cedulaFormateada;
    }
    
}