package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.Response;
import server.entity.Team;
import server.entity.process.Process;
import server.service.ProcessService;
import server.service.TeamService;


@RestController
@RequestMapping("/create")
public class CreateController {

    private final TeamService teamService;
    
    private final ProcessService processService;
    
    @Autowired
    public CreateController(TeamService teamService, ProcessService processService) {
    
        this.teamService = teamService;
        this.processService = processService;
    }
    
    @PostMapping(value = "/team", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createTeam(@RequestParam Team team) {
        
        return teamService.createTeam(team);
    }
    
    @PostMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createProcess(@RequestParam Process process) {
        
        return processService.createProcess(process);
    }
    
}
