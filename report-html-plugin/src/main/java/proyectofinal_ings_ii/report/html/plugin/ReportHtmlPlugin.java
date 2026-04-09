package proyectofinal_ings_ii.report.html.plugin;

import interfacePlugin.IReportPlugin;
import java.util.List;
import models.AppointmentRep;

public class ReportHtmlPlugin implements IReportPlugin {

    @Override
    public String generateReport(List<AppointmentRep> data) {

        StringBuilder html = new StringBuilder();

        html.append("<html>\n");
        html.append("  <head>\n");
        html.append("    <title>Reporte de Citas</title>\n");
        html.append("  </head>\n");
        html.append("  <body>\n");

        html.append("    <h2>Reporte de Citas</h2>\n");

        html.append("    <table border='1'>\n");

        html.append("      <tr>\n");
        html.append("        <th>Codigo Cita</th>\n");
        html.append("        <th>Fecha</th>\n");
        html.append("        <th>Nombre Paciente</th>\n");
        html.append("        <th>ID Paciente</th>\n");
        html.append("        <th>Nombre Profesional</th>\n");
        html.append("      </tr>\n");

        for (AppointmentRep a : data) {
            html.append("      <tr>\n");
            html.append("        <td>").append(a.getCodApp()).append("</td>\n");
            html.append("        <td>").append(a.getDate()).append("</td>\n");
            html.append("        <td>").append(a.getNamePat()).append("</td>\n");
            html.append("        <td>CC.").append(a.getIdPat()).append("</td>\n");
            html.append("        <td>Dr(a).").append(a.getNameProff()).append("</td>\n");
            html.append("      </tr>\n");
        }

        html.append("    </table>\n");
        html.append("  </body>\n");
        html.append("</html>\n");

        return html.toString();
    }
}