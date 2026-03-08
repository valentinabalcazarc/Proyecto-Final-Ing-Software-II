package proyectofinal_ings_ii.report.json.plugin;

import com.google.gson.*;
import interfacePlugin.IReportPlugin;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import models.AppointmentRep;

public class ReportJsonPlugin implements IReportPlugin {

    @Override
    public String generateReport(List<AppointmentRep> data) {

        // Crear Gson con soporte para LocalDate
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                    @Override
                    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));
                    }
                })
                .setPrettyPrinting()  // Para que el JSON sea legible
                .create();

        return gson.toJson(data);
    }
}