package server.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import server.entity.process.StepId;

import java.io.IOException;

public class StepIdDeserializer extends StdDeserializer<StepId> {
    
    public StepIdDeserializer(Class<?> vc) {
        
        super(vc);
    }
    
    @Override
    public StepId deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        
        return new StepId(node.get("processId").textValue(), node.get("title").textValue());
    }
    
}
