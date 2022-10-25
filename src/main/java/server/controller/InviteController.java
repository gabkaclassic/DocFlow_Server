package server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.Response;
import server.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/invite")
public class InviteController {
    
    private final TeamService teamService;
    
    @Autowired
    public InviteController(TeamService teamService) {
    
        this.teamService = teamService;
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addParticipantIntoTeam(@RequestParam String username, @RequestParam String teamId) {
        
        return teamService.addParticipant(username, teamId);
    }
    
    @PostMapping(value = "/many", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addParticipantsIntoTeam(@RequestParam String usernames, @RequestParam String teamId) throws JsonProcessingException {
        
        return teamService.addParticipants(usernames, teamId);
    }
    
}
