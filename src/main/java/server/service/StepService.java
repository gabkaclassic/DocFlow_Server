package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.controller.response.StepResponse;
import server.entity.process.Step;
import server.repository.StepRepository;

import java.util.Optional;

@Service
public class StepService {
    
    private final StepRepository repository;
    
    private final ParticipantService participantService;
    
    @Autowired
    public StepService(StepRepository repository, ParticipantService participantService) {
        
        this.repository = repository;
        this.participantService = participantService;
    }
    
    public Optional<Step> findById(Long id) {
        
        
        return repository.findById(id);
    }
    
    public StepResponse getStep(Long participantId, Long stepId) {
    
    
        var step = findById(stepId);
        var participant = participantService.findById(participantId);
        var response = new StepResponse();
        if(step.isPresent() && participant.isPresent()) {
            response.setParticipant(participant.get());
            response.setStep(step.get());
            response.setMessage(StepResponse.SUCCESS_LOAD);
            response.setStatus(Response.STATUS_SUCCESS);
        }
        else {
            response.setStatus(Response.STATUS_ERROR);
            response.setMessage(StepResponse.STEP_DOES_NOT_EXISTS);
        }
        
        return response;
    }
}
