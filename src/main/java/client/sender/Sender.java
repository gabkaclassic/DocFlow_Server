package client.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import server.entity.Team;
import server.entity.process.Participant;
import server.entity.process.Process;

import java.io.IOException;
import java.net.URISyntaxException;

public class Sender {
    
    private static final String BASE_URL = "http://localhost:8080/";
    private static final ObjectMapper mapper = new ObjectMapper();
    
    private static String cookie = "";
    
    private String token;
    
    public void registration(String username, String password) throws IOException {
        
        var params = new LinkedMultiValueMap();
        params.add("username", username);
        params.add("password", password);
        send("user/registry", params);
        
    }
    
    public void createTeam() throws IOException {
        
        // Как пример работы, реализация будет другой
        
//        var team = new Team();
//        var participant = new Participant();
//        participant.setId(1);
//        team.setTitle("Some team");
//        team.setTeamLeader(participant);
//        team.addParticipant(participant);
//        var writer = mapper.writer().withDefaultPrettyPrinter();
//
//        var params = new LinkedMultiValueMap();
//        var teamString = writer.writeValueAsString(team);
//        params.add("team", teamString);
//        send("/create/team", params);
        
    }
    
    private void send(String url,  LinkedMultiValueMap params) throws IOException {
        
        var client = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultCookie("SESSION", cookie)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        UriSpec<RequestBodySpec> uriSpec = client.post();
        RequestBodySpec bodySpec = uriSpec.uri(BASE_URL + url);
        RequestHeadersSpec<?> headersSpec = bodySpec.body(
                BodyInserters.fromFormData(params)
        );
        
        var response = headersSpec.exchangeToMono(clientResponse -> {
    
            var session = clientResponse.cookies().getFirst("SESSION");
            
            if(session != null)
                cookie = session.getValue();
            
         return clientResponse.bodyToMono(String.class);
        });
    
        System.out.println(response.block());
    }
    
    public void login(String username, String password) throws IOException, URISyntaxException {
    
        var params = new LinkedMultiValueMap();
        params.add("username", username);
        params.add("password", password);
        send("user/login", params);
    }
    
    public void createProcess(Process process) throws IOException {
    
        var writer = mapper.writer().withDefaultPrettyPrinter();
        var params = new LinkedMultiValueMap();
        var teamString = writer.writeValueAsString(process);
        params.add("team", teamString);
        send("/create/process", params);
    }
}