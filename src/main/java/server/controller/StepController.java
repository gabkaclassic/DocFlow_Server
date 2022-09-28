package server.controller;

import server.controller.response.StepResponse;
import server.entity.user.User;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/step")
public class StepController {
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse getStepInfo(@AuthenticationPrincipal User user,
                                    @RequestParam Long stepId) {
        
        return new StepResponse(stepId, user.getClient());
    }
}
