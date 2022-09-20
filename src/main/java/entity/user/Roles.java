package entity.user;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    
    USER, ADMIN, LOCKED;
    
    public String getAuthority() {
        
        return name();
    }
}
