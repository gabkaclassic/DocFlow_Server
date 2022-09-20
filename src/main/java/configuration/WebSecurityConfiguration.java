package configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    
        security.authorizeHttpRequests()
                .antMatchers("/registration").permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/info").failureUrl("/failure/login")
                .and().logout().logoutUrl("/logout").permitAll().deleteCookies("JSESSIONID")
                .and().cors().and().csrf()
                .and().sessionManagement().invalidSessionUrl("/failure/session").maximumSessions(1).maxSessionsPreventsLogin(true);
        
        return security.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(BCryptPasswordEncoder encoder,
                                                       UserDetailsService userDetailsService,
                                                       HttpSecurity security) throws Exception {
        return security.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and().build();
    }

}
