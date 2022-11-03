package server.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import server.entity.process.document.DocumentId;

import java.io.IOException;

public class DocumentIdDeserializer extends StdDeserializer<DocumentId> {
    
    public DocumentIdDeserializer() {
        
        this(null);
    }
    
    public DocumentIdDeserializer(Class<?> vc) {
        
        super(vc);
    }
    
    @Override
    public DocumentId deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
    
        return new DocumentId(node.get("processId").asLong(), node.get("title").textValue());
    }
    
}
