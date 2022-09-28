package server.controller.response;

import server.entity.process.Participant;
import server.entity.process.Step;
import server.entity.user.Client;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public StepResponse(Long id, Client client) {
        setStep(stepService.findById(id));
        setParticipant(participantService.findByClient(client));
    }
}
