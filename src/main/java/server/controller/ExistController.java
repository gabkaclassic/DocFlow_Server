package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.ExistResponse;
import server.service.TeamService;
import server.service.UserService;

@RestController
@RequestMapping("/exist")
public class ExistController {
    private final TeamService teamService;
    
    private final UserService userService;
    
    @Autowired
    public ExistController(TeamService teamService, UserService userService) {
    
        this.teamService = teamService;
        this.userService = userService;
    }
    
    @GetMapping(value = "/team", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExistResponse teamExists(@RequestParam String title) {
        
        return teamService.exists(title);
    }
    
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExistResponse userExists(@RequestParam String username) {
        
        return userService.exists(username);
    }
    
}
