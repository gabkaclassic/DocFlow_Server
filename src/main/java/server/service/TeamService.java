package server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.ExistResponse;
import server.controller.response.Response;
import server.entity.Team;
import server.repository.TeamRepository;

import java.util.List;

@Service
public class TeamService {
    
    private final TeamRepository repository;
    
    private final ParticipantService participantService;
    
    private final ObjectMapper mapper;
    
    @Autowired
    public TeamService(TeamRepository repository, ParticipantService participantService, ObjectMapper mapper) {
        
        this.repository = repository;
        this.participantService = participantService;
        this.mapper = mapper;
    }
    
    public Response createTeam(Team team) {
        
        var teamLeader = participantService.findById(team.getTeamLeaderId()).get();
        team.setTeamLeaderId(teamLeader.getId());
        
        save(team);
        
        return Response.successResponse(Response.SUCCESS_CREATING);
    }
    
    private void save(Team team) {
        
        repository.save(team);
    }
    
    public Response addParticipant(String username, String teamId) {
        
        var participant = participantService.findByOwnerUsername(username);
        var teamOptional = repository.findById(teamId);
        
        if(teamOptional.isEmpty())
            return Response.errorResponse(Response.TEAM_NOT_EXIST);
        
        var team = teamOptional.get();
        
        team.addParticipant(participant.getOwner().getUsername());
        participant.addTeam(team);
        repository.save(team);
        participantService.save(participant);
        
        return Response.successResponse(Response.SUCCESS_INVITE);
    }
    
    public Response addParticipants(String usernames, String teamId) throws JsonProcessingException {
        
        var teamOptional = repository.findById(teamId);
        
        if(teamOptional.isEmpty())
            return Response.errorResponse(Response.TEAM_NOT_EXIST);
        
        var team = teamOptional.get();
    
        List<String> list = mapper.readValue(usernames, List.class);
        
        list.stream()
            .map(Object::toString)
            .map(participantService::findByOwnerUsername)
            .peek(participant -> participant.addTeam(team))
            .forEach(participantService::save);
        
        return Response.successResponse(Response.SUCCESS_INVITE);
    }
    
    public void defineTeam(Team team) {
        
        team.getParticipants().stream()
                .map(participantService::findByOwnerUsername)
                .forEach(p -> p.addTeam(team));
    }
    
    public ExistResponse exists(String title) {
        
        return ExistResponse.builder()
                .exist(repository.existsById(title)).build()
                .status(Response.STATUS_SUCCESS);
    }
    
    public Response removeParticipant(String username, String teamId) {
        
        var team = repository.findById(teamId).get();
        var participant = participantService.findByOwnerUsername(username);
        
        team.removeParticipant(username);
        participant.removeTeam(team);
        repository.save(team);
        participantService.save(participant);
        
        return Response.successResponse(Response.SUCCESS_REFUSE);
    }
}
