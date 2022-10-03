package server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.Response;
import server.entity.process.Participant;

import java.util.List;

@RestController
@RequestMapping("/create")
public class CreateController {

    @PostMapping
    public Response createTeam(@RequestParam String title,
                               @RequestParam List<String> participantNameList) {
        
    }
}
