package service;

import entity.process.Process;
import entity.user.Client;
import entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ClientRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository repository;
    
    public Client findByAccount(User account) {
        
        return repository.findByAccount(account);
    }
    
    public Set<Process> getProcesses(Client client) {
        
        return client.getTeams().stream()
                .flatMap(team -> team.getProcesses().stream())
                .collect(Collectors.toCollection(HashSet::new));
    }
}
