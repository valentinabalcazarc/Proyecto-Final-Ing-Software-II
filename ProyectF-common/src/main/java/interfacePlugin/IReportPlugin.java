package interfacePlugin;

import java.util.List;
import models.AppointmentRep;

public interface IReportPlugin{
    
    String generateReport(List<AppointmentRep> data);
}
