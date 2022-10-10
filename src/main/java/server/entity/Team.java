package server.entity;

import lombok.Data;
import server.entity.process.Participant;
import server.entity.process.Process;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams")
@Data
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String title;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Participant> participants;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Process> processes;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Participant teamLeader;
    
    public void addParticipant(Participant participant) {
        
        participants.add(participant);
    }
}
