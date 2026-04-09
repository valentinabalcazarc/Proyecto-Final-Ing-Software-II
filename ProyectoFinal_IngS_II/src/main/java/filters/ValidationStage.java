package filters;

import java.util.List;
import models.Appointment;

public class ValidationStage implements IFilter<List<Appointment>, Boolean>{

    @Override
    public Boolean filter(List<Appointment> inp) {
        //True -> esta llena
        //False -> esta vacia
        return !inp.isEmpty();
    }

}
