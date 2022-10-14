package server.entity.process;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.deserializer.StepDeserializer;
import server.entity.process.document.Document;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "steps")
@NoArgsConstructor
@Data
@JsonDeserialize(using = StepDeserializer.class)
public class Step {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private int number;
    
    @Column
    private String title;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "process_documents"
    )
    private Set<Document> documents = new HashSet<>();
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
            name = "step_participant_rule",
            joinColumns = @JoinColumn(name = "participant_id")
    )
    @MapKeyColumn(name = "step_id")
    private Map<Long, Rules> rules = new HashMap<>();
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Participant> participants = new HashSet<>();
    
}

