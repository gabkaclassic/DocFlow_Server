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

@Entity
@Table(name = "participants")
@NoArgsConstructor
@Getter
@Setter
public class Participant implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User owner;
    
    @ManyToMany
    @JoinTable(
            name = "teams_participants",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Team> teams;
    
    public void addTeam(Team team) {
        
        teams.add(team);
    }
}
