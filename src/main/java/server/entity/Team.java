package server.entity;

import server.entity.process.Process;
import server.entity.user.Client;
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
    private Set<Client> participants;
    
    @OneToMany
    private Set<Process> processes;
}
