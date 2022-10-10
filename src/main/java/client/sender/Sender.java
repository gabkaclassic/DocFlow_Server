package client.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import server.controller.response.GeneralInfoResponse;
import server.controller.response.StepResponse;
import server.entity.process.document.Document;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Sender {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String BASE_URL = "http://localhost:8080/";
    private String token;
    
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
    public void logout(String username) throws IOException{
        var params = new LinkedMultiValueMap();
        params.add("username",username);
        send("user/logout", params);}
    public  void  login(String username, String password) throws IOException{
        var params = new LinkedMultiValueMap();
        params.add("username", username);
        params.add("password", password);
        var response = mapper.readValue(send("/user/login", params), GeneralInfoResponse.class);
        System.out.println(response);
    }
    public void GetUserInfo(String username) throws IOException {

        var params = new LinkedMultiValueMap();
        params.add("username", username);
        var response = mapper.readValue(send("/info", params), StepResponse.class);
        var documents = response.getStep().getDocuments();

        List<String> files = documents.stream().map(d -> d.getType().getDefaultFormat()).toList();

    }
    private void refreshToken() throws IOException {
        
        send(BASE_URL, new LinkedMultiValueMap());
    }

    private String send(String url,  LinkedMultiValueMap params) throws IOException {
    ;

//        var client = WebClient.builder()
//                .baseUrl(BASE_URL)
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//
//        UriSpec<RequestBodySpec> uriSpec = client.post();
//        RequestBodySpec bodySpec = uriSpec.uri(BASE_URL + url);
//        RequestHeadersSpec<?> headersSpec = bodySpec.body(
//                BodyInserters.fromFormData(params)
//        );
//        var result = headersSpec.exchangeToMono(response -> Mono.just(response.headers().header("Set-Cookie").get(0))).block();
//
//        token = result.substring(result.indexOf("=") + 1, result.indexOf(";"));
//        System.out.println(token);
//
        var client = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultCookie("XSRF-TOKEN", token)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        var uriSpec = client.post();
        var bodySpec = uriSpec.uri(BASE_URL + url);
        bodySpec.cookie("XSRF-TOKEN", token);
                RequestHeadersSpec<?> headersSpec = bodySpec.body(
                BodyInserters.fromFormData(params)
        );
        headersSpec.cookie("XSRF-TOKEN", token);
//        params.add("_csrf", token);

        var result = headersSpec.exchangeToMono(response -> {
//            if(response.statusCode().isError())
//                return Mono.just(response.headers().header("Set-Cookie").get(0));
            return response.bodyToMono(String.class);
        }).block();
        System.out.println(result);
        
//        token = result.substring(result.indexOf("=") + 1, result.indexOf(";"));
//        System.out.println(token);

        return result;
    }
    
}
