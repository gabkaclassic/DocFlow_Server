package controller;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SampleController {

    private final AmqpAdmin admin;
    private final AmqpTemplate template;
    
    public SampleController(AmqpAdmin admin, AmqpTemplate template) {
        
        this.admin = admin;
        this.template = template;
    }
    
    @PostMapping("/emit")
    public ResponseEntity<String> emit(@RequestBody String message) {
        
        template.convertAndSend("queue", message);
        
        return ResponseEntity.ok("Success");
    }
}
