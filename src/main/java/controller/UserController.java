package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;
import service.UserService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private ObjectWriter writer;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private UserService userService;
    
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() throws JsonProcessingException {
        
        return "Fuck you";
    }
    
    @PostMapping(value = "/registry", produces = MediaType.APPLICATION_JSON_VALUE)
    public String registration(@RequestParam String login,
                               @RequestParam String password) throws JsonProcessingException {
        
        return String.valueOf(userService.save(login, password));
    }
    
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public String logout(@RequestParam String login) {
        
        userService.logout(login);
        
        return "";
    }
    
}


