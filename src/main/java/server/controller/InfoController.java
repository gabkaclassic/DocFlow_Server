package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.GeneralInfoResponse;
import server.entity.user.User;
import server.service.ParticipantService;

@RestController
@RequestMapping("/info")
public class InfoController {
    
    private final ParticipantService participantService;
    
    @Autowired
    public InfoController(ParticipantService participantService) {
        
        this.participantService = participantService;
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralInfoResponse getGeneralInfo(@AuthenticationPrincipal User user) {
        
        return participantService.getProcessesAndTeams(user);
    }
}
