package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.controller.response.Response;
import server.entity.Team;
import server.entity.process.Participant;
import server.repository.TeamRepository;

import java.util.Objects;

@Service
@Transactional
public class TeamService {
    
    private final TeamRepository repository;
    
    private final ParticipantService participantService;
    
    @Autowired
    public TeamService(TeamRepository repository, ParticipantService participantService) {
        
        this.repository = repository;
        this.participantService = participantService;
    }
    
    
    @Transactional
    public Response createTeam(Team team) {
        
        var teamLeader = participantService.findById(team.getTeamLeaderId()).get();
        team.setTeamLeaderId(teamLeader.getId());
        
        save(team);
        
        var response = new Response();
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(Response.SUCCESS_CREATING);
        
        return response;
    }
    
    private void save(Team team) {
        
        repository.save(team);
    }
    
    public Response addParticipant(String username, String teamId) {
        
        var participant = participantService.findByOwnerUsername(username);
        var teamOptional = repository.findById(teamId);
    
        var response = new Response();
        
        if(teamOptional.isEmpty()) {
            
            response.setStatus(Response.STATUS_ERROR);
            response.setMessage(Response.TEAM_NOT_EXIST);
            
            return response;
        }
        
        var team = teamOptional.get();
        
        team.addParticipant(participant.getOwner().getUsername());
        participant.addTeam(team);
        repository.save(team);
        participantService.save(participant);
        
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(Response.SUCCESS_INVITE);
        
        return response;
    }
    
    
    @Transactional
    public void defineTeam(Team team) {
        
        team.getParticipants().stream()
                .map(participantService::findByOwnerUsername)
                .forEach(p -> p.addTeam(team));
    }
}
