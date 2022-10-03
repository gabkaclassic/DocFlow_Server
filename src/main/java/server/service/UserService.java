package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.entity.process.Participant;
import server.entity.user.User;
import server.repository.ParticipantRepository;
import server.repository.UserRepository;

import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    
    private static final int MIN_LOGIN_LENGTH = 2;
    private static final int MAX_LOGIN_LENGTH = 64;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final Pattern PASSWORD_PATTERN_FIRST = Pattern.compile("[а-яА-Яa-zA-Z]+");
    private static final Pattern PASSWORD_PATTERN_SECOND = Pattern.compile("[0-9]+");
    private static final Pattern PASSWORD_PATTERN_THIRD = Pattern.compile("[^а-яА-Я0-9a-zA-Z]+");
    
    private final UserRepository userRepository;
    
    private final ParticipantRepository participantRepository;
    
    private final BCryptPasswordEncoder encoder;
    
    @Autowired
    public UserService(UserRepository userRepository, ParticipantRepository participantRepository, BCryptPasswordEncoder encoder) {
        
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
        this.encoder = encoder;
    }
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return userRepository.findByUsername(username);
    }
    
    public Response registration(String login, String password) {
     
        
        if(userRepository.findByUsername(login) != null)
            return Response.builder()
                    .status(Response.STATUS_ERROR)
                    .message(Response.USER_ALREADY_EXISTS)
                    .build();
        
        if(!checkLogin(login))
            return Response.builder()
                    .status(Response.STATUS_ERROR)
                    .message(Response.INVALID_LOGIN)
                    .build();
        
        if(!checkPassword(password))
            return Response.builder()
                    .status(Response.STATUS_ERROR)
                    .message(Response.WEAK_PASSWORD)
                    .build();
        
        var newUser = new User();
        newUser.setUsername(login);
        newUser.setPassword(encoder.encode(password));
        var newParticipant = new Participant();
        newUser.setClient(newParticipant);
        newParticipant.setOwner(newUser);
        userRepository.save(newUser);
        participantRepository.save(newParticipant);
        
        return Response.builder()
                .status(Response.STATUS_SUCCESS)
                .message(Response.SUCCESS_REGISTRATION)
                .build();
    }
    
    public Response logout(User user) {
        
        userRepository.logout(user.getId());
        
        return Response.builder()
                .status(Response.STATUS_SUCCESS)
                .message(Response.SUCCESS_LOGOUT)
                .build();
    }
    
    public void login(User user) {
        
        userRepository.login(user.getId());
    }

    private boolean checkLogin(String login) {
        
        return login != null && login.length() >= MIN_LOGIN_LENGTH && login.length() <= MAX_LOGIN_LENGTH;
    }
    
    private boolean checkPassword(String password) {
        
        return password != null && password.length() >= MIN_PASSWORD_LENGTH
                && PASSWORD_PATTERN_FIRST.matcher(password).find()
                && PASSWORD_PATTERN_SECOND.matcher(password).find()
                && PASSWORD_PATTERN_THIRD.matcher(password).find();
    }
}
