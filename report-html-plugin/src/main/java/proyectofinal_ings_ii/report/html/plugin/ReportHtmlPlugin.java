package proyectofinal_ings_ii.report.html.plugin;

import interfacePlugin.IReportPlugin;
import java.util.List;
import models.Appointment;

public class ReportHtmlPlugin implements IReportPlugin<String>{

    @Override
    public String generateReport(List<Appointment> data) {

        StringBuilder html = new StringBuilder();

        html.append("<html>");
        html.append("<head>");
        html.append("<title>Reporte de Citas</title>");
        html.append("</head>");
        html.append("<body>");

        html.append("<h2>Reporte de Citas</h2>");

        html.append("<table border='1'>");
        
        html.append("<tr>");
        html.append("<th>Codigo Cita</th>");
        html.append("<th>Fecha</th>");
        html.append("<th>Nombre Paciente</th>");
        html.append("<th>ID Paciente</th>");
        html.append("<th>Nombre Profesional</th>");
        html.append("</tr>");

        for (Appointment a : data) {
            html.append("<tr>");
            
            html.append("<td>").append(a.getCodApp()).append("</td>");
            html.append("<td>").append(a.getDate()).append("</td>");
            html.append("<td>").append(a.getNamePat()).append("</td>");
            html.append("<td>").append(a.getIdPat()).append("</td>");
            html.append("<td>").append(a.getNameProff()).append("</td>");
            
            html.append("</tr>");
        }

        html.append("</table>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }
}