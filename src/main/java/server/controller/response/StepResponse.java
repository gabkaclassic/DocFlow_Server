package server.controller.response;

import lombok.Builder;
import lombok.Data;
import server.entity.process.step.Step;

/**
 * Тип ответа для получения информации о шаге
 * @see Response
 * @see Step
 * */
@Data
@Builder
public class StepResponse extends Response {
    
    public static final String DOES_NOT_EXISTS = "The entity with such parameters doesn't exists";
    
    private Step step;
    
    public StepResponse message(String message) {
        
        setMessage(message);
        
        return this;
    }
    
    public StepResponse status(String status) {
        
        setStatus(status);
        
        return this;
    }
}
