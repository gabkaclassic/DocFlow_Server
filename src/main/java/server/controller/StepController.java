package server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import server.controller.response.StepResponse;
import server.entity.process.Process;
import server.entity.process.Step;
import server.service.StepService;

@RestController
@RequestMapping("/step")
public class StepController {
    
    private final StepService stepService;
    
    private final ObjectMapper mapper;
    
    @Autowired
    public StepController(StepService stepService, ObjectMapper mapper) {
        
        this.stepService = stepService;
        this.mapper = mapper;
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse getStepInfo(@RequestParam Long stepId) {

        return stepService.getStep(stepId);
    }
    
    @PostMapping(value = "/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse approve(@RequestParam String process) throws JsonProcessingException {
        
        return stepService.approve(mapper.readValue(process, Process.class));
    }
    
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse updateStep(@RequestParam String step) throws JsonProcessingException {
        
        return stepService.update(mapper.readValue(step, Step.class));
    }
}
