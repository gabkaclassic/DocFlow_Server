package server.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.Team;
import server.entity.process.Participant;
import server.entity.process.Process;
import server.util.JSONUtils;

import java.io.IOException;

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
        team.setId(node.get("id").asLong());
        team.setTitle(node.get("title").textValue());
        
        team.setTeamLeader((Participant) JSONUtils.getObject(node, "teamLeader", Participant.class));
        team.setParticipants(JSONUtils.splitObjects(node, "participants", Participant.class)
                .map(Participant.class::cast)
                .toList()
        );
        team.setProcesses(JSONUtils.splitObjects(node, "processes", Process.class)
                .map(Process.class::cast)
                .toList()
        );
        return team;
    }
}
