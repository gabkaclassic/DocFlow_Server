package server.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import server.entity.Team;
import server.entity.process.Participant;
import server.entity.process.Process;
import server.entity.user.User;
import server.service.ParticipantService;

import java.util.Set;

@Data
@NoArgsConstructor
public class InfoResponse extends Response{
    
    private static final String SERVER_ERROR = "Error on server side";
    
    private Participant participant;
    
    private Set<Team> teams;
    
    private Set<Process> processes;
    
    public static InfoResponse createResponse(User user, ParticipantService service) {
        
        var response = new InfoResponse();
        if(user == null) {
            response.setStatus(Response.STATUS_ERROR);
            response.setMessage(SERVER_ERROR);
        }
        else {
            var participant = user.getClient();
            response.setParticipant(participant);
            response.setTeams(participant.getTeams());
            response.setProcesses(service.getProcesses(participant));
            response.setStatus(Response.STATUS_SUCCESS);
            response.setMessage("Success info loading");
        }
        
        return response;
    }
    
}
