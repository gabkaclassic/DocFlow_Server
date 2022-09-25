package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
@PropertySources({
        @PropertySource("classpath:application.properties")
})

@EnableJpaRepositories("repository")
@EntityScan(basePackages = {"entity"})
public class Application {
    
    public static void main(String[] args) throws IOException {
    
        SpringApplication.run(Application.class, args);
        var sender = new Sender();
        sender.defaultSend();
    }
}
