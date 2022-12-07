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
import server.entity.team.Team;
import server.entity.process.Process;
import server.entity.process.step.Step;
import server.service.DocumentService;
import server.service.ProcessService;
import server.service.StepService;
import server.service.TeamService;

/**
 * Контроллер для обновления состояния процессов и шагов
 * @see Response
 * */
@RestController
@RequestMapping("/update")
public class UpdateController {
    
    private final ObjectMapper mapper;
    
    private final StepService stepService;
    
    private final TeamService teamService;
    
    private final ProcessService processService;
    
    private final DocumentService documentService;
    
    @Autowired
    public UpdateController(ObjectMapper mapper, StepService stepService, TeamService teamService, ProcessService processService, DocumentService documentService) {
    
        this.mapper = mapper;
        this.stepService = stepService;
        this.teamService = teamService;
        this.processService = processService;
        this.documentService = documentService;
    }
    
    /**
     * Обработка обновления шага
     * @see Step
     * */
    @PostMapping(value = "/step", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateStep(@RequestParam String step) throws JsonProcessingException {
        
        return stepService.update(mapper.readValue(step, Step.class));
    }
    
    /**
     * Обработка обновления документов
     * @see server.entity.process.document.Document
     * */
    @PostMapping(value = "/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateDocuments(@RequestParam String documents) {
        
        return documentService.update(documents);
    }
    /**
     * Обработка привязки процесса к команде
     * */
    @PostMapping(value = "/team/addProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateTeam(@RequestParam String team, @RequestParam String process) throws JsonProcessingException {
        
        return teamService.addProcess(mapper.readValue(team, Team.class), mapper.readValue(process, Process.class));
    }
    
    /**
     * Обработка завершения процесса
     * @see Process
     * */
    @PostMapping(value = "/process/finish", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response finishProcess(@RequestParam String processId) {
        
        return processService.finish(processId);
    }
}
