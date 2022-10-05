package server.controller.response;

import lombok.*;
import server.entity.process.Participant;
import server.entity.process.Step;
import org.springframework.beans.factory.annotation.Autowired;
import server.entity.user.User;
import server.service.ParticipantService;
import server.service.StepService;

@Data
@NoArgsConstructor
public class StepResponse {
    
    public static final String SUCCESS_LOAD = "Success loading of data";
    public static final String STEP_DOES_NOT_EXISTS = "The step doesn't exists";
    
    @Autowired
    private StepService stepService;
    
    @Autowired
    private ParticipantService participantService;
    
    private String status;
    
    private String message;
    
    private Step step;
    
    private Participant participant;
}
