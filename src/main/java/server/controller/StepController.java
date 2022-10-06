package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.StepResponse;
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
    public StepResponse getStepInfo(@RequestParam Long participantId,
                                    @RequestParam Long stepId) {

        return stepService.getStep(participantId, stepId);
    }
}

