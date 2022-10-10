package server.controller.response;

import lombok.*;
import server.entity.process.Participant;
import server.entity.process.Step;
import org.springframework.beans.factory.annotation.Autowired;
import server.entity.user.User;
import server.service.ParticipantService;
import server.service.StepService;

@Data
@Builder
public class StepResponse extends Response {
    
    public static final String STEP_DOES_NOT_EXISTS = "The step doesn't exists";
    
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
