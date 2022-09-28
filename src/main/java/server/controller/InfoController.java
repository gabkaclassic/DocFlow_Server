package server.controller;

import server.controller.response.InfoResponse;
import server.entity.user.User;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoResponse getGeneralInfo(@AuthenticationPrincipal User user) {
        
        return new InfoResponse(user.getClient());
    }
}
