package server.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import server.entity.process.document.Document;
import server.entity.process.document.DocumentId;
import server.util.JSONUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentDeserializer extends StdDeserializer<Document> {
    
    public DocumentDeserializer() {
        
        this(null);
    }
    
    public DocumentDeserializer(Class<?> vc) {
        
        super(vc);
    }
    
    @Override
    public Document deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        var document = new Document();
        document.setId(JSONUtils.getObject(node, "id", DocumentId.class));
        document.setFile(JSONUtils.getObject(node, "file", byte[].class));
        document.setFormat(node.get("format").textValue());
        document.setComments(JSONUtils.splitObjects(node, "comments", String.class)
                .collect(Collectors.toList())
        );
        document.getComments().forEach(System.out::println);
        document.setResources(JSONUtils.splitObjects(node, "resources", String.class)
                .collect(Collectors.toList())
        );
        
        return document;
    }
    
}
