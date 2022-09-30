package server.entity;

import server.entity.process.Participant;
import server.entity.process.Process;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teams")
@Data
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany
    private Set<Participant> participants;
    
    @OneToMany
    private Set<Process> processes;
}
