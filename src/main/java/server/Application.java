package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.context.WebServerPortFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
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
    
    public static void main(String[] args) {
    
        var application = new SpringApplication(Application.class);
        application.addListeners(new WebServerPortFileWriter(), new ApplicationPidFileWriter());
        application.run();
    }
}
