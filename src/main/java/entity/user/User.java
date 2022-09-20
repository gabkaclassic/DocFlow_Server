package entity.user;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String password;
    
    @Column
    private String username;
    
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
