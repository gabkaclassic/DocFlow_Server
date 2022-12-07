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
        
        return teamService.sendInvite(username, teamId);
    }
    
    @PostMapping(value = "/access", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response accessInvite(@RequestParam Long inviteId) {
        
        return teamService.accessInvite(inviteId);
    }
    
    @PostMapping(value = "/refuse", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response refuseInvite(@RequestParam Long inviteId) {
        
        return teamService.refuseInvite(inviteId);
    }
    
    /**
     * Обработка добавления пользователей в команду
     * */
    @PostMapping(value = "/many", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addParticipantsIntoTeam(@RequestParam String usernames, @RequestParam String teamId) throws JsonProcessingException {
        
        return teamService.sendInvites(usernames, teamId);
    }
    /**
     * Обработка удаления пользователя из команды
     * */
    @PostMapping(value = "/kick", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response kickParticipant(@RequestParam String username, @RequestParam String teamId) {
        
        return teamService.removeParticipant(username, teamId);
    }
}
