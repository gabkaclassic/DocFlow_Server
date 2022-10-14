package server.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.deserializer.TeamDeserializer;
import server.entity.process.Participant;
import server.entity.process.Process;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@JsonDeserialize(using = TeamDeserializer.class)
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String title;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "teams")
    private List<Participant> participants = new ArrayList<>();
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Process> processes = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Participant teamLeader;
    
    public void addParticipant(Participant participant) {
        
        participants.add(participant);
    }
}

