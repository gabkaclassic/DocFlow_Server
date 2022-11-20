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
import java.util.Set;
import java.util.stream.Collectors;

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
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Team> teams;
    
    public void addTeam(Team team) {
        
        teams.add(team);
    }
    
    public void removeTeam(Team team) {
     
        teams = teams.stream()
                .filter(t -> !t.getTitle().equals(team.getTitle()))
                .collect(Collectors.toSet());
    }
    
    public String getUsername() {
        return owner.getUsername();
    }
}
