package server.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import server.entity.process.Participant;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @Column
    @JsonIgnore
    private String password;
    
    @Column(unique = true)
    private String username;
    
    @Column
    private boolean online;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Participant client;
    
    @ElementCollection(
        fetch = FetchType.EAGER,
        targetClass = Roles.class
    )
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;
    
    public User() {
        
        roles = new HashSet<>();
        roles.add(Roles.USER);
    }
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return roles;
    }
    
    public String getPassword() {
        
        return password;
    }
    
    public String getUsername() {
        
        return username;
    }
    
    @JsonIgnore
    public boolean isAccountNonExpired() {
        
        return true;
    }
    
    @JsonIgnore
    public boolean isAccountNonLocked() {
        
        return !roles.contains(Roles.LOCKED);
    }
    
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        
        return true;
    }
    
    @JsonIgnore
    public boolean isEnabled() {
        
        return true;
    }
}
