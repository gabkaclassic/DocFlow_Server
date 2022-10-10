package client;

import client.sender.Sender;

import java.io.IOException;

public class Application {
    
    public static void main(String[] args) throws IOException {
    
        var sender = new Sender();
        sender.login("user123", "lalala123!");
        //sender.login("user123", "lalala123!");
        //sender.logout("user123");

        
        }
}

