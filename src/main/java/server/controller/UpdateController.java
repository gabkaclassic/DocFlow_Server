package server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.Response;
import server.entity.Team;
import server.entity.process.Process;
import server.entity.process.Step;
import server.service.StepService;
import server.service.TeamService;

@RestController
@RequestMapping("/update")
public class UpdateController {
    
    private final ObjectMapper mapper;
    
    private final StepService stepService;
    
    private final TeamService teamService;
    
    @Autowired
    public UpdateController(ObjectMapper mapper, StepService stepService, TeamService teamService) {
    
        this.mapper = mapper;
        this.stepService = stepService;
        this.teamService = teamService;
    }
    
    @PostMapping(value = "/step", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateStep(@RequestParam String step) throws JsonProcessingException {
        
        return stepService.update(mapper.readValue(step, Step.class));
    }
    
    @PostMapping(value = "/team/addProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateTeam(@RequestParam String team, @RequestParam String process) throws JsonProcessingException {
        
        return teamService.addProcess(mapper.readValue(team, Team.class), mapper.readValue(process, Process.class));
    }
}
