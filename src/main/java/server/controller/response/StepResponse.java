package server.controller.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import server.entity.process.Participant;
import server.entity.process.Step;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import server.entity.user.User;
import server.service.ParticipantService;
import server.service.StepService;

@Data
@Builder
public class StepResponse extends Response {
    
    public static final String SUCCESS_LOAD = "Success loading of data";
    public static final String STEP_DOES_NOT_EXISTS = "The step doesn't exists";
    
    private final StepService stepService;
    
    private final ParticipantService participantService;
    
    private Step step;
    
    private Participant participant;
    
    @Autowired
    public StepResponse(StepService stepService, ParticipantService participantService) {
        
        this.stepService = stepService;
        this.participantService = participantService;
    }
    
    public StepResponse message(String message) {
        
        setMessage(message);
        
        return this;
    }
    
    public StepResponse status(String status) {
        
        setStatus(status);
        
        return this;
    }
}
