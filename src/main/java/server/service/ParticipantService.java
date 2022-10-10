package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.InfoResponse;
import server.controller.response.Response;
import server.entity.process.Participant;
import server.entity.process.Process;
import server.entity.user.User;
import server.repository.ParticipantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    
    private final ParticipantRepository repository;
    
    private final UserService userService;
    
    @Autowired
    public ParticipantService(ParticipantRepository repository, UserService userService) {
        
        this.repository = repository;
        this.userService = userService;
    }
    
    public Participant findByOwner(User user) {
        
        return repository.findByOwner(user);
    }
    
    public InfoResponse loadProcessesAndTeams(String username) {

        var user = userService.loadUserByUsername(username);
        
        if(user == null)
            return InfoResponse.builder().build()
                    .message(InfoResponse.SERVER_ERROR)
                    .status(Response.STATUS_ERROR);
        
        var participant = user.getClient();
        return InfoResponse.builder()
                .participant(participant)
                .teams(participant.getTeams())
                .processes(getProcesses(participant))
                .build()
                .status(Response.STATUS_SUCCESS)
                .message(InfoResponse.SUCCESS_LOADING);
        
    }
    
    public Optional<Participant> findById(Long participantId) {
        
        return repository.findById(participantId);
    }
    
    public InfoResponse loadTeams(String username) {
    
        var user = userService.loadUserByUsername(username);
    
        if(user == null)
            return InfoResponse.builder().build()
                    .status(Response.STATUS_ERROR)
                    .message(Response.SERVER_ERROR);
        
        var participant = user.getClient();
        
        return InfoResponse.builder()
                .teams(participant.getTeams())
                .build()
                .status(Response.STATUS_SUCCESS)
                .message(Response.SUCCESS_LOADING);
    }
    
    public InfoResponse loadProcesses(String username) {
        var user = userService.loadUserByUsername(username);
    
        if(user == null)
            return InfoResponse.builder().build()
                    .status(Response.STATUS_ERROR)
                    .message(Response.SERVER_ERROR);
    
        var participant = user.getClient();
    
        return InfoResponse.builder()
                .processes(getProcesses(participant))
                .build()
                .status(Response.STATUS_SUCCESS)
                .message(Response.SUCCESS_LOADING);
    }
    
    private List<Process> getProcesses(Participant participant) {
        
        return participant.getTeams().stream()
                .flatMap(team -> team.getProcesses().stream())
                .collect(Collectors.toList());
    }
    
    public Participant findByOwnerUsername(String username) {
        
        var user = userService.findByUsername(username);
        
        return user.getClient();
    }
    
    public void save(Participant participant) {
        
        repository.save(participant);
    }
}
