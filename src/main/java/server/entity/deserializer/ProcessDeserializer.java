package server.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.process.Process;
import server.entity.process.Step;
import server.util.JSONUtils;

import java.io.IOException;
import java.util.stream.Collectors;

public class ProcessDeserializer extends StdDeserializer<Process> {
    
    public ProcessDeserializer() {
        
        this(null);
    }
    
    public ProcessDeserializer(Class<?> vc) {
        
        super(vc);
    }
    
    @Override
    public Process deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        var process = new Process();
        
        process.setId(node.get("id").textValue());
        process.setTitle(node.get("title").textValue());
        process.setCurrentStep(node.get("currentStep").asInt());
        
        process.setSteps(JSONUtils.splitObjects(node, "steps", Step.class)
                .map(Step.class::cast)
                .collect(Collectors.toList())
        );
        
        return process;
    }
}
