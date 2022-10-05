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

import java.util.Optional;
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
    
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return userRepository.findByUsername(username);
    }
    
    public Response registration(String login, String password) {
     
        var response = new Response();
        response.setStatus(Response.STATUS_ERROR);
        
        if(userRepository.findByUsername(login) != null)
            response.setMessage(Response.USER_ALREADY_EXISTS);
        else if(!checkLogin(login))
            response.setMessage(Response.INVALID_LOGIN);
        else if(!checkPassword(password))
            response.setMessage(Response.WEAK_PASSWORD);
        
        if(response.error() && response.getMessage() != null)
            return response;
        
        var newUser = new User();
        newUser.setUsername(login);
        newUser.setPassword(encoder.encode(password));
        var newParticipant = new Participant();
        newUser.setClient(newParticipant);
        newParticipant.setOwner(newUser);
        userRepository.save(newUser);
        participantRepository.save(newParticipant);
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(Response.SUCCESS_REGISTRATION);
        
        return response;
    }
    
    public Optional<User> findById(Long id) {
        
        return userRepository.findById(id);
    }
    
    public Response logout(String username) {
        
        userRepository.logout(username);
        var response = new Response();
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(Response.SUCCESS_LOGOUT);
        
        return response;
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
