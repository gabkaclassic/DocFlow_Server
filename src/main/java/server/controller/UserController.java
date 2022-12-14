package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import server.controller.response.Response;
import server.service.UserService;

/**
 * Контроллер для обработки действий, связанных с аккаунтами пользователей
 * @see Response
 * */
@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        
        this.userService = userService;
    }
    
    /**
     * Обработка неудачной аутентификации
     * */
    @GetMapping(value = "/login/failure", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response failureLogin() {
        
        return Response.errorResponse(Response.INVALID_LOGIN_PROCESS);
    }
    
    /**
     * Обработка регистрации
     * */
    @PostMapping(value = "/registry", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response registration(@RequestParam String username,
                                 @RequestParam String password) {
        
        return userService.registration(username, password);
    }
    
    /**
     * Обработка неудачной логаута
     * */
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response logout(@RequestParam String username) {
        
        return userService.logout(username);
    }
}


