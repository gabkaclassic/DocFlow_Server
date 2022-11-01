package server.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.process.Participant;
import server.entity.process.Rules;
import server.entity.process.Step;
import server.entity.process.document.Document;
import server.util.JSONUtils;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class StepDeserializer extends StdDeserializer<Step> {
    
    public StepDeserializer() {
        
        this(null);
    }
    
    public StepDeserializer(Class<?> vc) {
        
        super(vc);
    }
    
    @Override
    public Step deserialize(JsonParser jp, DeserializationContext context)
            throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        var step = new Step();
        step.setId(node.get("id").asLong());
        step.setTitle(node.get("title").textValue());
        step.setNumber(node.get("number").asInt());
        var map = (Map) JSONUtils.getObject(node, "rules", Map.class);
        for (var o : map.keySet())
            step.getRules().put(String.valueOf(o), Rules.valueOf(String.valueOf(map.get(o))));
        
        step.setDocuments(JSONUtils.splitObjects(node, "documents", Document.class)
                .map(Document.class::cast)
                .collect(Collectors.toSet())
        );
        
        return step;
    }
}
