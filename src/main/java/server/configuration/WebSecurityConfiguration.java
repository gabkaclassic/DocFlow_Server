package server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final BCryptPasswordEncoder encoder;
    
    private final UserDetailsService userDetailsService;
    
    @Autowired
    public WebSecurityConfiguration(BCryptPasswordEncoder encoder, UserDetailsService userDetailsService) {
        
        this.encoder = encoder;
        this.userDetailsService = userDetailsService;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        
        security.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeHttpRequests()
                .antMatchers("/**").permitAll().anyRequest().authenticated()
                .and().formLogin().loginPage("/user/login").permitAll().defaultSuccessUrl("/user/login/success").failureUrl("/user/login/failure")
                .and().rememberMe()
                .and().logout().logoutUrl("/user/logout").deleteCookies("JSESSIONID")
                .and().cors()
                .and().exceptionHandling();
        
        return security.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(BCryptPasswordEncoder encoder,
                                                       UserDetailsService userDetailsService,
                                                       HttpSecurity security) throws Exception {
        return security
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and().build();
    }
    
    @Bean
    public HttpFirewall allowSlashInUrl() {
        
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowBackSlash(true);
        firewall.setAllowUrlEncodedDoubleSlash(true);
        
        
        return firewall;
    }
    
    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
}
