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
    
    private static final String USER_ALREADY_EXISTS = "Account with this login already exists";
    private static final String SUCCESS_REGISTRATION = "Registration was finished with success status";
    private static final String SUCCESS_LOGOUT = "Logout process finished with success status";
    private static final String WEAK_PASSWORD = "Password must contain 8 characters, at least 1 special symbol and at least 1 integer";
    private static final String INVALID_LOGIN = "Login must contain from 2 to 64 symbols";
    
    private static final int MIN_LOGIN_LENGTH = 2;
    private static final int MAX_LOGIN_LENGTH = 64;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final Pattern PASSWORD_PATTERN_FIRST = Pattern.compile("[а-яА-Яa-zA-Z]+");
    private static final Pattern PASSWORD_PATTERN_SECOND = Pattern.compile("[0-9]+");
    private static final Pattern PASSWORD_PATTERN_THIRD = Pattern.compile("[^а-яА-Я0-9a-zA-Z]+");
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ParticipantRepository participantRepository;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return userRepository.findByUsername(username);
    }
    
    public void registration(String login, String password, Response response) {
     
        if(userRepository.findByUsername(login) != null) {
            response.setStatus(Response.STATUS_ERROR);
            response.setMessage(USER_ALREADY_EXISTS);
            return;
        }
        if(!checkLogin(login)) {
            response.setStatus(Response.STATUS_ERROR);
            response.setMessage(INVALID_LOGIN);
            return;
        }
        if(!checkPassword(password)) {
            response.setStatus(Response.STATUS_ERROR);
            response.setMessage(WEAK_PASSWORD);
            return;
        }
        
        var newUser = new User();
        newUser.setUsername(login);
        newUser.setPassword(encoder.encode(password));
        var newParticipant = new Participant();
        newUser.setClient(newParticipant);
        newParticipant.setOwner(newUser);
        userRepository.save(newUser);
        participantRepository.save(newParticipant);
        
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(SUCCESS_REGISTRATION);
    
    }
    
    public void logout(User user, Response response) {
        
        userRepository.logout(user.getId());
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(SUCCESS_LOGOUT);
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
