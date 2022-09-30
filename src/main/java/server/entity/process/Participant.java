package server.entity.process;

import server.entity.Team;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "participants")
@NoArgsConstructor
@Data
public class Participant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    private User owner;
    
    @ManyToMany
    private Set<Team> teams;
}
