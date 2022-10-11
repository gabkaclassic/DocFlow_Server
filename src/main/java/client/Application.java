package client;

import client.sender.Sender;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.context.WebServerPortFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import server.entity.process.Participant;
import server.entity.process.Process;
import server.entity.process.Rules;
import server.entity.process.Step;
import server.entity.process.document.Comment;
import server.entity.process.document.Document;
import server.entity.process.document.Resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Application {
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        
        var mapper = new ObjectMapper();
        var writer = mapper.writer().withDefaultPrettyPrinter();

        var step1 = new Step();
        var process = new Process();
        var step = new Step();
        step.setTitle("Some step");
        step.setNumber(0);
        step.setNumber(1);
        var participant = new Participant();
        participant.setId(0);
        var document = new Document();
        document.setId("bla bla");
        document.setFile(new byte[]{1, 2});
        var resource = new Resource();
        resource.setValue("Value");
        resource.setDescription("Description");
        document.setResources(List.of(resource));
        var comment = new Comment();
        comment.setAuthor(participant);
        comment.setText("Text");
        document.setComments(List.of(comment));
        step.setRules(Map.of(participant.getId(), Rules.CHANGE));
        step.setDocuments(Set.of(document));
        process.setCurrentStep(step);
        process.setSteps(List.of(step, step1));

        var sender = new Sender();
        sender.login("user3", "1231231sdf2341!");
        sender.createProcess(process);

        
        }
}
