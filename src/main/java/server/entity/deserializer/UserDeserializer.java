package server.entity.deserializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import server.entity.user.User;

import java.io.IOException;

public class UserDeserializer extends StdDeserializer<User> {
    
    
    public UserDeserializer() {
        
        this(null);
    }
    
    public UserDeserializer(Class<?> vc) {
        
        super(vc);
    }
    
    @Override
    public User deserialize(JsonParser jp, DeserializationContext context)
            throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        
        var user = new User();
        
        
        user.setId(node.get("id").asLong());
        user.setUsername(node.get("username").textValue());
        
        return user;
    }
}

