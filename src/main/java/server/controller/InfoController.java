package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.InfoResponse;
import server.controller.response.ProcessResponse;
import server.service.ParticipantService;
import server.service.ProcessService;
import server.service.UserService;

/**
 * Контроллер для обработки процесса создания сущностей
 * */
@RestController
@RequestMapping("/info")
public class InfoController {
    
    private final ParticipantService participantService;
    
    private final ProcessService processService;
    
    private final UserService userService;
    
    @Autowired
    public InfoController(ParticipantService participantService, ProcessService processService, UserService userService) {
        
        this.participantService = participantService;
        this.processService = processService;
        this.userService = userService;
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoResponse getGeneralInfo(Authentication auth) {

        var username = auth.getName();
        
        userService.login(username);
        
        return participantService.loadProcessesAndTeams(username);
    }
    
    @GetMapping(value = "/teams", produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoResponse getTeamsInfo(Authentication auth) {
        
        return participantService.loadTeams(auth.getName());
    }
    
    @GetMapping(value = "/processes", produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoResponse getProcessesInfo(Authentication auth) {
        
        return participantService.loadProcesses(auth.getName());
    }
    
    @GetMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProcessResponse getProcessInfo(String processId) {
        
        return processService.getInfo(processId);
    }
    
}
