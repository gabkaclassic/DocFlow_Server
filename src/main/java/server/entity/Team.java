<<<<<<< HEAD
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
=======
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
>>>>>>> cbe5782ff02c56d8e48fca8cbd6828bffbcc2b45
