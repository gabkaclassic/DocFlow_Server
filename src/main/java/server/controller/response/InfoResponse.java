package server.controller.response;

import server.entity.Team;
import server.entity.process.Process;
import server.entity.user.Client;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import server.service.ClientService;

import java.util.Set;

@Data
@NoArgsConstructor
public class InfoResponse {
    
    @Autowired
    private ClientService clientService;
    
    private Client client;
    
    private Set<Team> teams;
    
    private Set<Process> processes;
    
    public InfoResponse(Client client) {
        setClient(client);
        setTeams(client.getTeams());
        setProcesses(clientService.getProcesses(client));
    }
    
}
