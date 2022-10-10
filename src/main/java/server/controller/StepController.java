package server.controller;

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
    
    @Autowired
    public StepController(StepService stepService) {
        
        this.stepService = stepService;
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse getStepInfo(@RequestParam Long stepId) {

        return stepService.getStep(stepId);
    }
    
    @PostMapping(value = "/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse approve(@RequestParam Process process) {
        
        return stepService.approve(process);
    }
    
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse updateStep(@RequestParam Step step) {
        
        return stepService.update(step);
    }
}
