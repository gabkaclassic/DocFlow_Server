package controller.response;

import entity.Team;
import entity.process.Process;
import entity.user.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponse {
    
    private Client client;
    
    private Set<Team> teams;
    
    private Set<Process> processes;
    
}
