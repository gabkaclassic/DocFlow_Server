package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.InfoResponse;
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
    public InfoResponse getGeneralInfo(Authentication auth) {

        return participantService.loadProcessesAndTeams(auth.getName());
    }
    
    @GetMapping(value = "/teams", produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoResponse getTeamsInfo(Authentication auth) {
        
        return participantService.loadTeams(auth.getName());
    }
    
    @GetMapping(value = "/processes", produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoResponse getProcessesInfo(Authentication auth) {
        
        return participantService.loadProcesses(auth.getName());
    }
    
}
