package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import sender.Sender;

import java.io.IOException;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(value = {
        "controller",
        "configuration",
        "entity",
        "repository",
        "service"
})
public class Application {
    
    public static void main(String[] args) throws IOException {
    
        SpringApplication.run(Application.class, args);
        Sender sender = new Sender();
        sender.defaultSend();
    }
}
