package server.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.context.WebServerPortFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(value = {
        "server/controller",
        "server/controller/response",
        "server/configuration",
        "server/entity",
        "server/repository",
        "server/service"
})
@PropertySources({
        @PropertySource("classpath:application.properties")
})

@EnableJpaRepositories("server/repository")
@EntityScan(basePackages = {"server/entity"})
public class Application {
    
    public static void main(String[] args) throws IOException {
    
        var application = new SpringApplication(Application.class);
        application.addListeners(new WebServerPortFileWriter(), new ApplicationPidFileWriter());
        application.run();
    }
}
