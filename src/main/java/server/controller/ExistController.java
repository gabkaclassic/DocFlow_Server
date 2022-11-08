package server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.ExistResponse;
import server.entity.process.document.DocumentId;
import server.service.DocumentService;
import server.service.ProcessService;
import server.service.TeamService;
import server.service.UserService;

@RestController
@RequestMapping("/exist")
public class ExistController {
    private final TeamService teamService;
    
    private final UserService userService;
    
    private final ProcessService processService;
    
    private final DocumentService documentService;
    
    private final ObjectMapper mapper;
    
    @Autowired
    public ExistController(TeamService teamService, UserService userService, ProcessService processService, DocumentService documentService, ObjectMapper mapper) {
    
        this.teamService = teamService;
        this.userService = userService;
        this.processService = processService;
        this.documentService = documentService;
        this.mapper = mapper;
    }
    
    @GetMapping(value = "/team", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExistResponse teamExists(@RequestParam String title) {
        
        return teamService.exists(title);
    }
    
    @GetMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExistResponse processExists(@RequestParam String title) {
        
        return processService.exists(title);
    }
    
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExistResponse userExists(@RequestParam String username) {
        
        return userService.exists(username);
    }
    
    @GetMapping(value = "/document", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExistResponse documentExists(@RequestParam String processId, @RequestParam String title) {
        
        return documentService.exists(new DocumentId(processId, title));
    }
}
