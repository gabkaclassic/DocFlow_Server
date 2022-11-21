package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

public class Test {
    
    public static void main(String[] args) throws JsonProcessingException {
    
        var mapper = new ObjectMapper();
        var writer = mapper.writer().withDefaultPrettyPrinter();
        byte[] arr = new byte[] {1, -2, -33, 122};
        var s = writer.writeValueAsString(arr);
        System.out.println(s);
        System.out.println(arr);
    }
    
}
