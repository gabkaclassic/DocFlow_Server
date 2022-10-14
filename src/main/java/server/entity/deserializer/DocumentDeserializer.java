package server.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.process.document.Comment;
import server.entity.process.document.Document;
import server.entity.process.document.DocumentType;
import server.entity.process.document.Resource;
import server.util.JSONUtils;

import java.io.IOException;
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
        document.setId(node.get("id").textValue());
        document.setFile((byte[]) JSONUtils.getObject(node, "file", byte[].class));
        document.setTitle(node.get("title").textValue());
        document.setType((DocumentType) JSONUtils.getObject(node, "type", DocumentType.class));
        document.setComments(JSONUtils.splitObjects(node, "comments", Comment.class)
                .map(Comment.class::cast)
                .collect(Collectors.toList())
        );
        
        document.setResources(JSONUtils.splitObjects(node, "resources", Resource.class)
                .map(Resource.class::cast)
                .collect(Collectors.toList())
        );
        
        return document;
    }
    
}
