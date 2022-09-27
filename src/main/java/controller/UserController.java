package controller;

import controller.response.SimpleResponse;
import entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping(value = "/registry", produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleResponse registration(@RequestParam String login,
                                       @RequestParam String password) {
        
        var response = new SimpleResponse();
        if(userService.save(login, password)){
            response.setStatus("success");
            response.setMessage("success registration");
        }
        else {
            response.setStatus("error");
            response.setMessage("error of registration");
        }
        
        return response;
    }
    
    @GetMapping("/login/success")
    public String postLogin(@AuthenticationPrincipal User user) {
        
        userService.login(user);
        
        return "redirect:/info";
    }
    
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleResponse logout(@AuthenticationPrincipal User user) {
        
        userService.logout(user);
        
        return new SimpleResponse("success", "Logout process finished with success status");
    }
    
}


