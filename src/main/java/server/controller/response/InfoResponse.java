package server.controller.response;

import lombok.Builder;
import lombok.Data;
import server.entity.Team;
import server.entity.process.Participant;
import server.entity.process.Process;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class InfoResponse extends Response {
    
    private Participant participant;
    
    private List<Team> teams;
    
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
