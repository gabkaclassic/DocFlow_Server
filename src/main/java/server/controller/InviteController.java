package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.Response;
import server.service.TeamService;


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
    
}
