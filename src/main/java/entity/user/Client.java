package entity.user;

import entity.Team;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private User account;
    
    @ManyToMany
    private Set<Team> teams;
}
