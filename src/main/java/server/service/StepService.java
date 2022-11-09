package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.controller.response.StepResponse;
import server.entity.process.Process;
import server.entity.process.Step;
import server.entity.process.StepId;
import server.repository.StepRepository;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class StepService {
    
    private final StepRepository repository;
    
    private final ParticipantService participantService;
    
    private final ProcessService processService;
    
    private final DocumentService documentService;
    
    @Autowired
    public StepService(StepRepository repository, ParticipantService participantService, ProcessService processService, DocumentService documentService) {
        
        this.repository = repository;
        this.participantService = participantService;
        this.processService = processService;
        this.documentService = documentService;
    }
    
    public Step findById(StepId id) {
        
        
        return repository.findById(id).orElseThrow();
    }
    
    public StepResponse getStep(StepId stepId) {
        
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
