package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.entity.Team;
import server.repository.TeamRepository;

@Service
public class TeamService {
    
    private final TeamRepository repository;
    
    private final ParticipantService participantService;
    
    @Autowired
    public TeamService(TeamRepository repository, ParticipantService participantService) {
        
        this.repository = repository;
        this.participantService = participantService;
    }
    
    public Response createTeam(Team team) {
        
        repository.save(team);
        
        var response = new Response();
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(Response.SUCCESS_CREATING);
        
        return response;
    }
    
    public Response addParticipant(String username, Long teamId) {
        
        var participant = participantService.findByOwnerUsername(username);
        var teamOptional = repository.findById(teamId);
    
        var response = new Response();
        
        if(teamOptional.isEmpty()) {
            
            response.setStatus(Response.STATUS_ERROR);
            response.setMessage(Response.TEAM_NOT_EXIST);
            
            return response;
        }
        
        var team = teamOptional.get();
        
        team.addParticipant(participant);
        repository.save(team);
        participantService.save(participant);
        
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(Response.SUCCESS_INVITE);
        
        return response;
    }
}
