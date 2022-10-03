package server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@Configuration
public class EncryptPasswordConfiguration {
    
    private static final int PASSWORD_STRENGTH = 8;
    
    @Bean
    public BCryptPasswordEncoder encoder() {
        
        return new BCryptPasswordEncoder(PASSWORD_STRENGTH);
    }
}
