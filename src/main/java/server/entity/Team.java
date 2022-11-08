package server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.deserializer.TeamDeserializer;
import server.entity.process.Participant;
import server.entity.process.Process;
import server.entity.user.Roles;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@JsonDeserialize(using = TeamDeserializer.class)
public class Team implements Serializable {
    
    @Id
    @Column
    private String title;
    @ElementCollection(
            fetch = FetchType.EAGER,
            targetClass = String.class
    )
    @CollectionTable(
            name = "teams_participants",
            joinColumns = @JoinColumn(name = "team_id", nullable = false)
    )
    private Set<String> participants = new HashSet<>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Process> processes = new ArrayList<>();
    
    @Column
    private Long teamLeaderId;
    
    public void addParticipant(String participant) {
        
        participants.add(participant);
    }
    
    public void removeParticipant(String username) {
        
        participants.remove(username);
    }
    
    public void addProcess(Process process) {
        
        processes.add(process);
    }
    
    public void addParticipants(List<String> participants) {
        
        this.participants.addAll(participants);
    }
}

