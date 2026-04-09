package filters;

public interface IFilter<Input, Output>{
    
    Output filter(Input inp);
    
}
