package entity.process;

import entity.process.document.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    
    @OneToMany
    @JoinTable(
            name = "process_documents"
    )
    private Set<Document> documents;
    
    @OneToOne
    private Step nextStep;
}
