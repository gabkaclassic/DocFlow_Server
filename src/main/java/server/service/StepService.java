package server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.controller.response.StepResponse;
import server.entity.process.Step;
import server.entity.process.StepId;
import server.repository.StepRepository;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class StepService {
    
    private final StepRepository repository;
    
    @Autowired
    public StepService(StepRepository repository) {
        
        this.repository = repository;
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
    
            log.warn("No such step exception", e);
            
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
}
