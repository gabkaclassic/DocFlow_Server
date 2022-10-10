package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    public Response addParticipantIntoTeam(@RequestParam String username, @RequestParam Long teamId) {
        
        return teamService.addParticipant(username, teamId);
    }
    
}
