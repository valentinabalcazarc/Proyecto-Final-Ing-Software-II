package interfacePlugin;

import java.util.List;
import models.Appointment;

public interface IReportPlugin{
    
    String generateReport(List<Appointment> data);
}
