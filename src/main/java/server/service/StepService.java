package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.controller.response.StepResponse;
import server.entity.process.Process;
import server.entity.process.Step;
import server.repository.StepRepository;

import java.util.Optional;

@Service
public class StepService {
    
    private final StepRepository repository;
    
    private final ParticipantService participantService;
    
    private final ProcessService processService;
    
    @Autowired
    public StepService(StepRepository repository, ParticipantService participantService, ProcessService processService) {
        
        this.repository = repository;
        this.participantService = participantService;
        this.processService = processService;
    }
    
    public Optional<Step> findById(Long id) {
        
        
        return repository.findById(id);
    }
    
    public StepResponse getStep(Long stepId) {
        
        var step = findById(stepId);
        
        if(step.isPresent())
            return StepResponse.builder()
                    .step(step.get()).build()
                    .status(Response.STATUS_SUCCESS)
                    .message(Response.SUCCESS_LOADING);
    
        return StepResponse.builder().build()
                .status(Response.STATUS_ERROR)
                .message(StepResponse.STEP_DOES_NOT_EXISTS);
    }
    
    public StepResponse approve(Process process) {
        
        process.nextStep();
        processService.save(process);
        
        return StepResponse.builder()
                .step(process.getCurrentStep()).build()
                .status(Response.STATUS_SUCCESS)
                .message(Response.SUCCESS_LOADING);
    }
    
    public StepResponse update(Step step) {
        
        repository.save(step);
        
        return StepResponse.builder().build()
                .status(Response.STATUS_SUCCESS)
                .message(Response.SUCCESS_LOADING);
    }
}
