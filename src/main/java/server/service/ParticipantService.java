package server.service;

import server.entity.process.Participant;
import server.entity.user.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.repository.ParticipantRepository;

@Service
public class ParticipantService {
    
    @Autowired
    private ParticipantRepository repository;
    
    public Participant findByClient(Client client) {
        
        return repository.findByOwner(client);
    }
}
