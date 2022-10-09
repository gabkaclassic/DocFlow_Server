package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.GeneralInfoResponse;
import server.controller.response.Response;
import server.entity.Team;
import server.entity.process.Participant;
import server.entity.process.Process;
import server.entity.user.User;
import server.repository.ParticipantRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
    
    public Set<Process> getProcesses(Participant participant) {
        
        return participant.getTeams().stream()
                .flatMap(team -> team.getProcesses().stream())
                .collect(Collectors.toCollection(HashSet::new));
    }
    
    public GeneralInfoResponse getProcessesAndTeams(String username) {

        var user = userService.loadUserByUsername(username);
        
        if(user == null)
            return GeneralInfoResponse.builder().build()
                    .message(GeneralInfoResponse.SERVER_ERROR)
                    .status(Response.STATUS_ERROR);
        else {
            var participant = user.getClient();
            return GeneralInfoResponse.builder()
                    .participant(participant)
                    .teams(
                            participant.getTeams().stream()
                                    .map(Team::toString)
                                    .collect(Collectors.toSet()
                                    )
                    )
                    .processes(getProcesses(participant))
                    .build()
                    .status(Response.STATUS_SUCCESS)
                    .message(GeneralInfoResponse.SUCCESS_LOADING);
        }
    }
    
    public Optional<Participant> findById(Long participantId) {
        
        return repository.findById(participantId);
    }
}
