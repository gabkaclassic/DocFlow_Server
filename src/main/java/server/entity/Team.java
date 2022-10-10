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
    
    @Column
    private String title;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Participant> participants;
    
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Process> processes;
}

