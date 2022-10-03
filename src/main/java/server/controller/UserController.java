package server.controller;

import server.controller.response.Response;
import server.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    
    @GetMapping("/login/success")
    public void postLogin(@AuthenticationPrincipal User user, HttpServletResponse response) throws IOException {
        
        userService.login(user);
        
        response.sendRedirect("/info");
    }
    
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response logout(@AuthenticationPrincipal User user) {
        
        return userService.logout(user);
    }
    
}


