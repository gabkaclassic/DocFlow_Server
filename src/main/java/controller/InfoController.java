package controller;

import controller.response.InfoResponse;
import entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ClientService;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private ClientService clientService;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoResponse getGeneralInfo(@AuthenticationPrincipal User user) {
        
        var client = clientService.findByAccount(user);
    
        return new InfoResponse(client, client.getTeams(), clientService.getProcesses(client));
    }
}
