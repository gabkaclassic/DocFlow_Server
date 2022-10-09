package client.sender;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;

public class Sender {
    
    private static final String BASE_URL = "http://localhost:8080/";
    
    private String token;
    
    
    public Sender() {
    
    }
    
    public void registration(String username, String password) throws IOException {
        
        var params = new LinkedMultiValueMap();
        params.add("username", username);
        params.add("password", password);
        send("user/registry", params);
        
    }
    
    
    private void refreshToken() throws IOException {
        
//        send(BASE_URL, new LinkedMultiValueMap());
    }
    
    private void send(String url,  LinkedMultiValueMap params) throws IOException {
    
        var client = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        UriSpec<RequestBodySpec> uriSpec = client.post();
        RequestBodySpec bodySpec = uriSpec.uri(BASE_URL + url);
        RequestHeadersSpec<?> headersSpec = bodySpec.body(
                BodyInserters.fromFormData(params)
        );
        
        var response = headersSpec.exchangeToMono(clientResponse -> Mono.just(clientResponse.statusCode().toString()));
        System.out.println(response.block());
        
    }
    
    public void login(String username, String password) throws IOException, URISyntaxException {
    
        var params = new LinkedMultiValueMap();
        params.add("username", username);
        params.add("password", password);
        send("user/login", params);
    }
}