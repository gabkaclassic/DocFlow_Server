package server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import server.controller.response.Response;
import server.service.TeamService;

/**
 * Контроллер для обработки процесса добавления участников в команду/удаления из неё
 * @see Response
 * */
@RestController
@RequestMapping("/invite")
public class InviteController {
    
    private final TeamService teamService;
    
    @Autowired
    public InviteController(TeamService teamService) {
    
        this.teamService = teamService;
    }
    
    /**
     * Обработка добавления пользователя в команду
     * */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addParticipantIntoTeam(@RequestParam String username, @RequestParam String teamId) {
        
        return teamService.addParticipant(username, teamId);
    }
    
    /**
     * Обработка добавления пользователей в команду
     * */
    @PostMapping(value = "/many", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addParticipantsIntoTeam(@RequestParam String usernames, @RequestParam String teamId) throws JsonProcessingException {
        
        return teamService.addParticipants(usernames, teamId);
    }
    /**
     * Обработка удаления пользователя из команды
     * */
    @PostMapping(value = "/refuse", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response refuseInvite(@RequestParam String username, @RequestParam String teamId) {
        
        return teamService.removeParticipant(username, teamId);
    }
    
}
