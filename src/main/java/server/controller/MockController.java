package server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MockController {

    @GetMapping(value = {
            "",
            "/favicon.ico",
            "/robots.txt"
    })
    public String homepage() {
        
        return "";
    }
    
}
