package server.service;

import server.entity.process.Participant;
import server.entity.process.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.user.User;
import server.repository.ParticipantRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    
    @Autowired
    private ParticipantRepository repository;
    
    public Participant findByOwner(User user) {
        
        return repository.findByOwner(user);
    }
    
    public Set<Process> getProcesses(Participant participant) {
        
        return participant.getTeams().stream()
                .flatMap(team -> team.getProcesses().stream())
                .collect(Collectors.toCollection(HashSet::new));
    }
}
