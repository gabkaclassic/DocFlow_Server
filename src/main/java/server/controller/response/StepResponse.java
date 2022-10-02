package server.controller.response;

import server.entity.process.Participant;
import server.entity.process.Step;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import server.entity.user.User;
import server.service.ParticipantService;
import server.service.StepService;

@Data
@NoArgsConstructor
public class StepResponse extends Response {
    
    private static final String SUCCESS_LOAD = "Success loading of data";
    private static final String STEP_DOES_NOT_EXISTS = "The step doesn't exists";
    
    @Autowired
    private StepService stepService;
    
    @Autowired
    private ParticipantService participantService;
    
    private Step step;
    
    private Participant participant;

    public StepResponse(Long id, User user) {
        
        var step = stepService.findById(id);
        
        if(step.isPresent()) {
    
            setStep(step.get());
            setParticipant(participantService.findByOwner(user));
            setStatus(Response.STATUS_SUCCESS);
            setMessage(SUCCESS_LOAD);
        }
        else {
            setStatus(Response.STATUS_ERROR);
            setMessage(STEP_DOES_NOT_EXISTS);
        }
    }
}
