package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import server.controller.response.Response;
import server.service.UserService;
@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        
        this.userService = userService;
    }
    
    @PostMapping(value = "/registry", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response registration(@RequestParam String username,
                                 @RequestParam String password) {
        
        return userService.registration(username, password);
    }

    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response logout(@RequestParam String username) {
        
        return userService.logout(username);
    }
}


