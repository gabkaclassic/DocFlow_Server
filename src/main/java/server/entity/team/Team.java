package server.entity.team;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import server.entity.deserializer.TeamDeserializer;
import server.entity.process.Process;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность "Команда"
 * */
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
    @JoinColumn(name = "team_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Process> processes = new HashSet<>();
    
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
    
    public void addParticipants(Collection<String> participants) {
        
        this.participants.addAll(participants);
    }
}

