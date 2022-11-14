package server.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import server.entity.Team;
import server.entity.process.Participant;
import server.entity.process.Process;
import server.util.JSONUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TeamDeserializer extends StdDeserializer<Team> {
    
    
    public TeamDeserializer() {
        
        this(null);
    }
    
    public TeamDeserializer(Class<?> vc) {
        
        super(vc);
    }
    
    @Override
    public Team deserialize(JsonParser jp, DeserializationContext context)
            throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        Team team = new Team();
        team.setTitle(node.get("title").textValue());
        
        team.setTeamLeaderId(node.get("teamLeaderId").asLong());
        team.setParticipants(Arrays.stream(node.get("participants").toPrettyString()
                        .replace("\"", "")
                        .replace("[", "").replace("]", "")
                        .split(","))
                .map(String::trim)
                .collect(Collectors.toSet())
        );
        team.setProcesses(JSONUtils.splitObjects(node, "processes", Process.class)
                .map(Process.class::cast)
                .collect(Collectors.toList())
        );
        return team;
    }
}
