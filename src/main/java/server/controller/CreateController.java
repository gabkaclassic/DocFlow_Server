package server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import server.controller.response.Response;
import server.entity.Team;
import server.entity.process.Process;
import server.service.ProcessService;
import server.service.TeamService;

/**
 * Контроллер для обработки процесса создания сущностей
 * @see Response
 * */
@RestController
@RequestMapping("/create")
public class CreateController {

    private final TeamService teamService;
    
    private final ProcessService processService;
    
    private final ObjectMapper mapper;
    
    @Autowired
    public CreateController(TeamService teamService, ProcessService processService, ObjectMapper mapper) {
    
        this.teamService = teamService;
        this.processService = processService;
        this.mapper = mapper;
    }
    
    /**
     * Обработка запроса создания команд
     * @see Team
     * */
    @PostMapping(value = "/team", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createTeam(@RequestParam String team) throws JsonProcessingException {
    
        return teamService.createTeam(mapper.readValue(team, Team.class));
    }
    
    /**
     * Обработка запроса создания процессов
     * @see Process
     * */
    @PostMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createProcess(@RequestParam String process) throws JsonProcessingException {

        return processService.createProcess(mapper.readValue(process, Process.class));
    }
    
}
