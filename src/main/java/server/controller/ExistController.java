package server.controller;

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

/**
 * Контроллер для получения информации о наличии сущности в БД
 * @see ExistResponse
 * */
@RestController
@RequestMapping("/exist")
public class ExistController {
    private final TeamService teamService;
    
    private final UserService userService;
    
    private final ProcessService processService;
    
    private final DocumentService documentService;
    
    @Autowired
    public ExistController(TeamService teamService, UserService userService, ProcessService processService, DocumentService documentService) {
    
        this.teamService = teamService;
        this.userService = userService;
        this.processService = processService;
        this.documentService = documentService;
    }
    /**
     * Обработка запроса о существовании команды
     * @see server.entity.Team
     * */
    @GetMapping(value = "/team", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExistResponse teamExists(@RequestParam String title) {
        
        return teamService.exists(title);
    }
    /**
     * Обработка запроса о существовании процесса
     * @see server.entity.process.Process
     * */
    @GetMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExistResponse processExists(@RequestParam String title) {
        
        return processService.exists(title);
    }
    
    /**
     * Обработка запроса о существовании пользователя
     * @see server.entity.user.User
     * */
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExistResponse userExists(@RequestParam String username) {
        
        return userService.exists(username);
    }
    
    /**
     * Обработка запроса о существовании документа
     * @see server.entity.process.document.Document
     * */
    @GetMapping(value = "/document", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExistResponse documentExists(@RequestParam String stepTitle, @RequestParam String title) {
        
        return documentService.exists(new DocumentId(stepTitle, title));
    }
}
