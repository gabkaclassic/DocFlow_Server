package server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.InfoResponse;
import server.controller.response.Response;
import server.entity.process.Participant;
import server.entity.user.User;
import server.repository.ParticipantRepository;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для сущности "Участник"
 * @see Participant
 * */
@Service
@Slf4j
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
                .processes(participant.getTeams().stream()
                        .flatMap(t -> t.getProcesses().stream())
                        .filter(p -> p.getSteps().stream()
                                .anyMatch(s -> s.getRules().containsKey(participant.getUsername()))
                        )
                        .collect(Collectors.toList())
                )
                .invites(participant.getInvites())
                .build()
                .status(Response.STATUS_SUCCESS)
                .message(InfoResponse.SUCCESS_LOADING);
    }
    
    public Optional<Participant> findById(Long participantId) {
        
        return repository.findById(participantId);
    }
    
    public InfoResponse loadTeams(String username) {
    
        var user = userService.loadUserByUsername(username);
    
        if(user == null) {
            
            log.debug("User doesn't exists", Thread.currentThread().getStackTrace());
            
            return InfoResponse.builder().build()
                    .status(Response.STATUS_ERROR)
                    .message(Response.SERVER_ERROR);
        }
        
        return InfoResponse.builder()
                .build()
                .status(Response.STATUS_SUCCESS)
                .message(Response.SUCCESS_LOADING);
    }
    
    public InfoResponse loadProcesses(String username) {
        var user = userService.loadUserByUsername(username);
    
        if(user == null) {
        
            log.debug("User doesn't exists", Thread.currentThread().getStackTrace());
        
            return InfoResponse.builder().build()
                    .status(Response.STATUS_ERROR)
                    .message(Response.SERVER_ERROR);
        }
    
        return InfoResponse.builder()
                .build()
                .status(Response.STATUS_SUCCESS)
                .message(Response.SUCCESS_LOADING);
    }
    
    public Participant findByOwnerUsername(String username) {
        
        var user = userService.loadUserByUsername(username);
        
        
        
        return user.getClient();
    }
    
    public void save(Participant participant) {
        
        repository.save(participant);
    }
}
