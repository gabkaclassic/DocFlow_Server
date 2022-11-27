package server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import server.controller.ErrorHandler;

/**
 * Бин для кодирования и проверки закодированных паролей
 *@see WebSecurityConfiguration#authenticationManager(BCryptPasswordEncoder, UserDetailsService, HttpSecurity)
 * */
@Configuration
public class EncryptPasswordConfiguration {
    
    private static final int PASSWORD_STRENGTH = 8;
    
    @Bean
    public BCryptPasswordEncoder encoder() {
        
        return new BCryptPasswordEncoder(PASSWORD_STRENGTH);
    }
}
