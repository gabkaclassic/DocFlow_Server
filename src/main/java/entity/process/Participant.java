package entity.process;

import entity.process.document.Document;
import entity.user.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "steps")
@NoArgsConstructor
@Data
public class Participant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    private Client owner;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
            name = "participant_document_rule",
            joinColumns = @JoinColumn(name = "participant_id")
    )
    private Map<Rules, Document> rules;
}
