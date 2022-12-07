package server.controller.response;

import lombok.Builder;
import lombok.Data;
import server.entity.team.Invite;
import server.entity.team.Team;
import server.entity.process.Participant;
import server.entity.process.Process;

import java.util.List;
import java.util.Set;

/**
 * Тип ответа для получения всей информации, связанной с конкретным аккаунтом участника
 * @see Response
 * @see Participant
 * */
@Builder
@Data
public class InfoResponse extends Response {
    
    private Participant participant;
    
    private Set<Team> teams;
    
    private List<Process> processes;
    
    private Set<Invite> invites;
    
    public InfoResponse message(String message) {
        
        setMessage(message);
        
        return this;
    }
    
    public InfoResponse status(String status) {
        
        setStatus(status);
        
        return this;
    }
}
