package server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import server.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private final BCryptPasswordEncoder encoder;
    
    private final UserDetailsService userDetailsService;
    
    private final UserService userService;
    
    @Autowired
    public WebSecurityConfiguration(BCryptPasswordEncoder encoder, UserDetailsService userDetailsService, UserService userService) {
        
        this.encoder = encoder;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
    
        security
                .authorizeRequests()
                .antMatchers( "/user/login", "/user/logout", "/user/registry").permitAll().anyRequest().authenticated()
                .and().formLogin().loginPage("/user/login").successHandler((request, response, authentication) -> {
                    var username = authentication.getName();
 
                    userService.login(username);
                }
                )
                .and().rememberMe()
                .and().cors()
                .and().exceptionHandling()
                .and().csrf().disable()
                .sessionManagement().maximumSessions(3).maxSessionsPreventsLogin(true);
        
        security.logout().logoutUrl("/user/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();
    
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
