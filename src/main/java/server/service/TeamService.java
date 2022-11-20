package server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.ExistResponse;
import server.controller.response.Response;
import server.entity.Team;
import server.entity.process.Process;
import server.repository.TeamRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
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
        
        try {
            var teamLeader = participantService.findById(team.getTeamLeaderId()).orElseThrow();
            team.setTeamLeaderId(teamLeader.getId());
    
            save(team);
        }
        catch (NoSuchElementException e) {
            log.warn("No such team leader exception", e);
            return Response.errorResponse(Response.NOT_EXIST);
        }
        return Response.successResponse(Response.SUCCESS_CREATING);
    }
    
    private void save(Team team) {
        
        repository.save(team);
    }
    
    public Response addParticipant(String username, String teamId) {
        
        try {
            var participant = participantService.findByOwnerUsername(username);
            var team = repository.findById(teamId).orElseThrow();
    
            team.addParticipant(participant.getOwner().getUsername());
            participant.addTeam(team);
            repository.save(team);
            participantService.save(participant);
    
        }
        catch (NoSuchElementException e) {
        
            log.warn("No such team exception", e);
            return Response.errorResponse(Response.NOT_EXIST);
        }
        
        return Response.successResponse(Response.SUCCESS_INVITE);
    }
    
    public Response addParticipants(String usernames, String teamId) throws JsonProcessingException {
        
        try {
        
            var team = repository.findById(teamId).orElseThrow();
        
            List<String> list = mapper.readValue(usernames, List.class);
            team.addParticipants(list);
        
            list.stream()
            .map(Object::toString)
            .map(participantService::findByOwnerUsername)
            .peek(participant -> participant.addTeam(team))
            .forEach(participantService::save);
        }
        catch (NoSuchElementException e) {
    
            log.warn("No such team exception", e);
            return Response.errorResponse(Response.NOT_EXIST);
        }
        
        return Response.successResponse(Response.SUCCESS_INVITE);
    }
    
    public ExistResponse exists(String title) {
        
        return ExistResponse.builder()
                .exist(repository.existsById(title)).build()
                .status(Response.STATUS_SUCCESS);
    }
    
    public Response removeParticipant(String username, String teamId) {
        
        try {
            
            var team = repository.findById(teamId).orElseThrow();
            var participant = participantService.findByOwnerUsername(username);
            if(team.getTeamLeaderId().equals(participant.getId()))
                return Response.errorResponse(Response.NOT_EXIST);
            
            team.removeParticipant(username);
            participant.removeTeam(team);
            participantService.save(participant);
            repository.save(team);
        }
        catch (NoSuchElementException e) {
            
            log.warn("No such team exception", e);
            return Response.errorResponse(Response.NOT_EXIST);
        }
        
        return Response.successResponse(Response.SUCCESS_REFUSE);
    }
    
    public Response addProcess(Team team, Process process) {
    
        var id = UUID.randomUUID().toString();
        process.setId(id);
    
        for(var step: process.getSteps()) {
        
            step.setProcessId(id);
            var title = step.getTitle();
        
            step.getDocuments().forEach(document -> document.setStepTitle(title));
        }
        
        team.addProcess(process);
        
        
        repository.save(team);
        
        return Response.successResponse(Response.SUCCESS_CREATING);
    }
}
