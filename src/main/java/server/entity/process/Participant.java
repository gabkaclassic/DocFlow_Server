package server.entity.process;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import server.entity.Team;
import server.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "participants")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Participant implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @ToString.Exclude
    private User owner;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Team> teams;
}
