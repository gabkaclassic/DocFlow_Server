package server.entity.process;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import server.entity.Team;
import server.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "participants")
@NoArgsConstructor
@Getter
@Setter
public class Participant implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User owner;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Team> teams;
    
    public void addTeam(Team team) {
        
        teams.add(team);
    }
    
    public void removeTeam(Team team) {
     
        teams.remove(team);
    }
    
    public String getUsername() {
        return owner.getUsername();
    }
}
