package proyectofinal_ings_ii.report.json.plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import interfacePlugin.IReportPlugin;
import java.util.List;
import models.Appointment;

public class ReportJsonPlugin implements IReportPlugin<String>{

    @Override
    public String generateReport(List<Appointment> data) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(data);
    }
}