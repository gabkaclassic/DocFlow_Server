package server.configuration;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import server.controller.response.InfoResponse;
import server.controller.response.Response;
import server.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * Класс-конфигурация приложения
 * Производит настройку доступа запросов, логику аутентификации и работы сессий
 * */
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {
    
    private final ObjectWriter writer;
    private final UserService userService;
    
    @Autowired
    public WebSecurityConfiguration(ObjectWriter writer, UserService userService) {
        this.writer = writer;
        this.userService = userService;
    }
    
    /**
     * Конфигурация аутентификации, доступа к отдельным доменам, работы сессий
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
    
        security
                .authorizeRequests()
                .antMatchers( "/user/login", "/user/logout", "/user/registry", "/", "/favicon.ico", "/robots.txt").permitAll().anyRequest().authenticated()
                .and().formLogin().loginPage("/user/login")
                .failureHandler((request, response, exception) -> {
                    try {
                
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                        response.getWriter().write(writer.writeValueAsString(
                                        InfoResponse.builder().build().status(Response.STATUS_ERROR).message(Response.INVALID_LOGIN_PROCESS)
                                )
                        );
                        
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                })
                .defaultSuccessUrl("/info")
                .and().rememberMe()
                .and().cors()
                .and().logout().logoutUrl("/user/logout")
                .clearAuthentication(false)
                .addLogoutHandler((request, response, authentication) -> {
                    
                    var username = authentication.getName();
                    userService.logout(username);
                    
                }).logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                    response.getWriter().write(writer.writeValueAsString(
                                    Response.successResponse(Response.SUCCESS_LOGOUT)
                            )
                    );
                })
                .deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll()
                .and().exceptionHandling()
                .and().csrf().disable()
                .sessionManagement().maximumSessions(3).maxSessionsPreventsLogin(true);
    
        return security.build();
    }
    /**
     * Конфигурация сервиса для сущности пользователей
     * @see UserService
     * и шифрования их паролей
     * @see EncryptPasswordConfiguration
     * */
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
    
    /**
     * Конфигурация обработки в URL разных вариаций слэшей
     * */
    @Bean
    public HttpFirewall allowSlashInUrl() {
        
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowBackSlash(true);
        firewall.setAllowUrlEncodedDoubleSlash(true);
        
        return firewall;
    }
}
