package server.service;

import org.springframework.stereotype.Service;
import server.entity.team.Invite;
import server.entity.team.Team;
import server.repository.InviteRepository;

@Service
public class InviteService {
    
    private final InviteRepository repository;
    
    public InviteService(InviteRepository repository) {
    
        this.repository = repository;
    }
    
    public void save(Invite invite) {
        
        repository.save(invite);
    }
    
    public void deleteAllByTeam(Team team) {
        
        repository.deleteByTeam(team);
    }
    public Invite findById(Long inviteId) {
        
        return repository.findById(inviteId).orElseThrow();
    }
    
    public void remove(Invite invite) {
        
        repository.delete(invite);
    }
}
