package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.controller.response.StepResponse;
import server.entity.process.Process;
import server.entity.process.Step;
import server.repository.StepRepository;

import java.util.Collection;
import java.util.NoSuchElementException;
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
    
    public Step findById(Long id) {
        
        
        return repository.findById(id).orElseThrow();
    }
    
    public StepResponse getStep(Long stepId) {
        
        Step step;
        StepResponse response;
        
        try {
            
            step = findById(stepId);
    
            response = StepResponse.builder()
                    .step(step).build()
                    .status(Response.STATUS_SUCCESS)
                    .message(Response.SUCCESS_LOADING);
        }
        catch (NoSuchElementException e) {
    
            response = StepResponse.builder().build()
                    .status(Response.STATUS_ERROR)
                    .message(e.getMessage());
        }
    
        return response;
    }
    
    public StepResponse approve(Long processId) {
        
        Process process;
        StepResponse response;
        
        try {
            
            process = processService.findById(processId);
            process.nextStep();
            processService.save(process);
    
            response = StepResponse.builder()
                    .step(process.getCurrentStep()).build()
                    .status(Response.STATUS_SUCCESS)
                    .message(Response.SUCCESS_LOADING);
        }
        catch (NoSuchElementException e) {
            
            response = StepResponse.builder().build()
                    .status(Response.STATUS_ERROR)
                    .message(e.getMessage());
        }
        
        return response;
    }
    
    public StepResponse refuse(Long processId) {
        Process process;
        StepResponse response;
    
        try {
        
            process = processService.findById(processId);
            process.previousStep();
            processService.save(process);
        
            response = StepResponse.builder()
                    .step(process.getCurrentStep()).build()
                    .status(Response.STATUS_SUCCESS)
                    .message(Response.SUCCESS_LOADING);
        }
        catch (NoSuchElementException e) {
        
            response = StepResponse.builder().build()
                    .status(Response.STATUS_ERROR)
                    .message(e.getMessage());
        }
    
        return response;
    }
    
    public Response update(Step step) {
        
        repository.save(step);
        
        return Response.successResponse(Response.SUCCESS_LOADING);
    }
    
    public void save(Step currentStep) {
        
        repository.save(currentStep);
    }
    
    public void saveAll(Collection<Step> steps) {
        
        repository.saveAll(steps);
    }
}
