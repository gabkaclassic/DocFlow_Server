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
import server.service.DocumentService;
import server.service.StepService;
import server.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/update")
public class UpdateController {
    
    private final ObjectMapper mapper;
    
    private final StepService stepService;
    
    private final TeamService teamService;
    
    private final DocumentService documentService;
    
    @Autowired
    public UpdateController(ObjectMapper mapper, StepService stepService, TeamService teamService, DocumentService documentService) {
    
        this.mapper = mapper;
        this.stepService = stepService;
        this.teamService = teamService;
        this.documentService = documentService;
    }
    
    @PostMapping(value = "/step", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateStep(@RequestParam String step) throws JsonProcessingException {
        
        return stepService.update(mapper.readValue(step, Step.class));
    }
    
    @PostMapping(value = "/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateDocuments(@RequestParam String documents) {
        
        return documentService.update(documents);
    }
    @PostMapping(value = "/team/addProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateTeam(@RequestParam String team, @RequestParam String process) throws JsonProcessingException {
        
        return teamService.addProcess(mapper.readValue(team, Team.class), mapper.readValue(process, Process.class));
    }
}
