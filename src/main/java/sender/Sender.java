package sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sender {
    
    public void defaultSend() throws IOException {
    
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet("http://localhost:8080/file");
    
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        builder.addBinaryBody(
//                "file", new File("C:\\Users\\Kuzmi\\Documents\\Groups.xlsx"), ContentType.APPLICATION_OCTET_STREAM, "Groups.xlsx");
//
//        HttpEntity multipart = builder.build();
//        httpPost.setEntity(multipart);
    
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("login", "John"));
//        params.add(new BasicNameValuePair("password", "pass"));
//        httpPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = client.execute(httpPost);
        response = client.execute(httpPost);
        System.out.println(response.getCode());
        var mapper = new ObjectMapper();
        var result = mapper.readValue(new String(response.getEntity().getContent().readAllBytes()), Response.class);
        var file = new File("");
        file.createNewFile();
        var out = new FileOutputStream(file);
        out.write(result.getDocument());
        client.close();
    }
    
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Response {
    
    private String title;
    
    private byte[] document;
}
