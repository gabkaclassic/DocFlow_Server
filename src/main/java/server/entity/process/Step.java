package server.entity.process;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.process.document.Document;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "steps")
@NoArgsConstructor
@Data
public class Step {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String title;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "process_documents"
    )
    private Set<Document> documents;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
            name = "step_participant_rule",
            joinColumns = @JoinColumn(name = "participant_id")
    )
    @MapKeyColumn(name = "step_id")
    private Map<Participant, Rules> rules;
    
    @OneToOne
    private Step nextStep;
    
}
