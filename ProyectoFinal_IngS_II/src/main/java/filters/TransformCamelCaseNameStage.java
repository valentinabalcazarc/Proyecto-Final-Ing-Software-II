package filters;

public class TransformCamelCaseNameStage implements IFilter<String, String>{

    @Override
    public String filter(String inp) {
                String[] palabras = inp.toLowerCase().split(" ");
        String resultado = "";

        for (String palabra : palabras) {

            if (!palabra.isEmpty()) {
                resultado += palabra.substring(0,1).toUpperCase() 
                           + palabra.substring(1) + " ";
            }
        }

        return resultado.trim();
    }
}