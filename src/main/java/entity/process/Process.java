package entity.process;

import entity.process.document.Document;
import entity.user.Client;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "processes")
public class Process {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToMany
    @JoinTable(
            name = "process_documents"
    )
    private Set<Document> documents;
    
    @OneToMany
    private Set<Client> currentParticipants;
    
    @ManyToOne
    @JoinTable(
            name = "processes_pattern"
    )
    private Pattern pattern;
}
