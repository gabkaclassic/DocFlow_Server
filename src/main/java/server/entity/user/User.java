package server.entity.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @Column
    private String password;
    
    @Column(unique = true)
    private String username;
    
    @Column
    private boolean online;
    
    @OneToOne
    private Client client;
    
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
    
    public boolean isAccountNonExpired() {
        
        return true;
    }
    
    public boolean isAccountNonLocked() {
        
        return !roles.contains(Roles.LOCKED);
    }
    
    public boolean isCredentialsNonExpired() {
        
        return true;
    }
    
    public boolean isEnabled() {
        
        return true;
    }
}
