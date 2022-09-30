package server.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import server.entity.Team;
import server.entity.process.Participant;
import server.entity.process.Process;
import server.service.ParticipantService;

import java.util.Set;

@Data
@NoArgsConstructor
public class InfoResponse {
    
    @Autowired
    private ParticipantService participantService;
    
    private Participant participant;
    
    private Set<Team> teams;
    
    private Set<Process> processes;
    
    public InfoResponse(Participant participant) {
        setParticipant(participant);
        setTeams(participant.getTeams());
        setProcesses(participantService.getProcesses(participant));
    }
    
}
