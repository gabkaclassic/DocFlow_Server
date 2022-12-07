package server.entity.process;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import server.entity.team.Invite;
import server.entity.team.Team;
import server.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сущность "Участник"
 * */
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
    
    @OneToMany(mappedBy = "candidate")
    @ToString.Exclude
    @JsonIgnore
    private Set<Invite> invites = new HashSet<>();
    
    public void addTeam(Team team) {
        
        teams.add(team);
    }
    
    public void removeTeam(Team team) {
     
        teams = teams.stream()
                .filter(t -> !t.getTitle().equals(team.getTitle()))
                .collect(Collectors.toSet());
    }
    
    public void addInvite(Invite invite) {
        
        invites.add(invite);
    }
    public void removeInvite(Invite invite) {
        
        invites = invites.stream()
                .filter(i -> !Objects.equals(i.getId(), invite.getId()))
                .collect(Collectors.toSet());;
    }
    public String getUsername() {
        return owner.getUsername();
    }
}
