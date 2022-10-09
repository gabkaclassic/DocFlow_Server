package client;

import client.sender.Sender;

import java.io.IOException;
import java.net.URISyntaxException;

public class Application {
    
    public static void main(String[] args) throws IOException, URISyntaxException {
    
        var sender = new Sender();
        sender.login("user123", "lalala123!");

        
        }
}
