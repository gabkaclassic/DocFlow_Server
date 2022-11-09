package server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import server.controller.response.StepResponse;
import server.entity.process.StepId;
import server.service.ProcessService;
import server.service.StepService;


@RestController
@RequestMapping("/step")
public class StepController {
    
    private final StepService stepService;
    
    private final ObjectMapper mapper;
    private final ProcessService processService;
    
    
    @Autowired
    public StepController(StepService stepService, ObjectMapper mapper, ProcessService processService) {
        
        this.stepService = stepService;
        this.mapper = mapper;
        this.processService = processService;
    }
    
    @GetMapping(value = "/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse approve(@RequestParam String processId) {
        
        return processService.nextStep(processId);
    }
    @GetMapping(value = "/refuse", produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse refuse(@RequestParam String processId) {
        
        return processService.previousStep(processId);
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse getStepInfo(@RequestParam String stepId) throws JsonProcessingException {

        return stepService.getStep(mapper.readValue(stepId, StepId.class));
    }
}
