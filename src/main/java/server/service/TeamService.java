package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.entity.process.Participant;
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
    
    public Response createTeam() {
        
        
        return null;
    } 
}
