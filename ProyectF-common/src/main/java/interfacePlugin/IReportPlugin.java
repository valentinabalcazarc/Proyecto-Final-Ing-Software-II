package interfacePlugin;

import java.util.List;
import models.Appointment;

public interface IReportPlugin <Output>{
    
    Output generateReport(List<Appointment> data);
}
