package entity;

import entity.process.Pattern;
import entity.process.Process;
import entity.user.Client;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToMany
    private Set<Client> participants;
    
    @OneToMany
    private Set<Process> processes;
    
    @OneToMany
    private Set<Pattern> patterns;
}
