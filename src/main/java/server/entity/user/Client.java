package server.entity.user;

import server.entity.Team;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
@Data
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private User account;
    
    @ManyToMany
    private Set<Team> teams;
}
