package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import server.controller.response.InfoResponse;
import server.entity.user.User;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.ParticipantService;

@RestController
@RequestMapping("/info")
public class InfoController {
    
    @Autowired
    private ParticipantService participantService;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoResponse getGeneralInfo(@AuthenticationPrincipal User user) {
        
        return InfoResponse.createResponse(user, participantService);
    }
}
