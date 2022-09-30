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
public class StepResponse {
    
    @Autowired
    private StepService stepService;
    
    @Autowired
    private ParticipantService participantService;
    
    private Step step;
    
    private Participant participant;

    public StepResponse(Long id, User user) {
        setStep(stepService.findById(id));
        setParticipant(participantService.findByOwner(user));
    }
}
