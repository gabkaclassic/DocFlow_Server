package server.controller.response;

import lombok.Builder;
import lombok.Data;
import server.entity.Team;
import server.entity.process.Participant;
import server.entity.process.Process;

import java.util.List;
import java.util.Set;

@Builder
@Data
public class InfoResponse extends Response {
    
    private Participant participant;
    
    private Set<Team> teams;
    
    private List<Process> processes;
    
    public InfoResponse message(String message) {
        
        setMessage(message);
        
        return this;
    }
    
    public InfoResponse status(String status) {
        
        setStatus(status);
        
        return this;
    }
}
