package client;

import client.sender.Sender;

import java.io.IOException;

public class Application {
    
    public static void main(String[] args) throws IOException {
    
        var sender = new Sender();
        sender.registration("user123", "lalala123!");
        
        }
}
