package server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MockController {

    @GetMapping
    public String homepage() {
        
        return "redirect:/user/login";
    }
    
    @GetMapping("/favicon.ico")
    public String favicon() {
    
        return "redirect:/user/login";
    }
    
}
