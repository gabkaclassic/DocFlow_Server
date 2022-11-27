package server.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import server.entity.deserializer.UserDeserializer;
import server.entity.process.Participant;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность "Пользователь"
 * */
@Entity
@Table(name = "users")
@Getter
@Setter
@JsonDeserialize(using = UserDeserializer.class)
public class User implements UserDetails, Serializable {

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
    @JsonIgnore
    private boolean online;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Participant client;
    
    /**
     * Роли пользователя
     * @see Roles
     * */
    @ElementCollection(
        fetch = FetchType.EAGER,
        targetClass = Roles.class
    )
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    @Enumerated(EnumType.STRING)
    @JsonIgnore
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
