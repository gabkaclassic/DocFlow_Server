package entity.process;

import entity.process.document.Document;
import entity.user.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "steps")
@NoArgsConstructor
@Data
public class Participant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
            name = "participant_document_rule",
            joinColumns = @JoinColumn(name = "participant_id")
    )
    @MapKeyColumn(name="rules")
    private Map<Document, Rules> rules;
}
