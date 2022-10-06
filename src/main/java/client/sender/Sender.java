package client.sender;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Sender {
    
    private static final String BASE_URL = "http://localhost:8080/";
    
    private String token;
    
    
    public Sender() {
    
    }
    
    public void registration(String username, String password) throws IOException {
        
//        refreshToken();
        var params = new LinkedMultiValueMap();
        params.add("username", username);
        params.add("password", password);
        send("user/registry", params);
        
    }
    
//    public void logout() throws IOException {
//
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("_csrf", token));
//        var response = send(BASE_URL + "/user/logout", params);
//
//    }
    
    private void refreshToken() throws IOException {
        
        send(BASE_URL, new LinkedMultiValueMap());
    }
    
    private void send(String url,  LinkedMultiValueMap params) throws IOException {
    
        var client = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", BASE_URL + url))
                .build();
    
        UriSpec<RequestBodySpec> uriSpec = client.post();
        RequestBodySpec bodySpec = uriSpec.uri(BASE_URL + url);
        params.add("_csrf", token);
        RequestHeadersSpec<?> headersSpec = bodySpec.body(
                BodyInserters.fromFormData(params)
        );
    
        var cookies = headersSpec.exchangeToMono(response -> {
            if(response.statusCode().isError())
                return Mono.just(response.headers().header("Set-Cookie").get(0));
            return response.bodyToMono(String.class);
        }).block();
        
        token = cookies.substring(cookies.indexOf("=") + 1, cookies.indexOf(";"));
    }
    
}

