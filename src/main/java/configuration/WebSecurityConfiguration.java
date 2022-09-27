package configuration;

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
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    
        security.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/**").permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/user/login").permitAll().defaultSuccessUrl("/login/success").failureUrl("/user/login/failure")
                .and().rememberMe()
                .and().logout().logoutUrl("/user/logout").permitAll().deleteCookies("JSESSIONID")
                .and().cors()
                .and().sessionManagement().invalidSessionUrl("/failure/session").maximumSessions(1).maxSessionsPreventsLogin(true);
        
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
