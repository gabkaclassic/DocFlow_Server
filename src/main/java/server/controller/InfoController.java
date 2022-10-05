package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.GeneralInfoResponse;
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
    public GeneralInfoResponse getGeneralInfo(@RequestParam String username) {

        return participantService.getProcessesAndTeams(username);
    }
}
