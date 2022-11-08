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
    
    @EmbeddedId
    private StepId id;
    
    @Column
    private Integer number;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinTable(
            name = "step_documents",
            joinColumns = {
                    @JoinColumn(name = "processId", referencedColumnName = "processId"),
                    @JoinColumn(name = "title", referencedColumnName = "title")
            }
    )
    private Set<Document> documents = new HashSet<>();
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
            name = "step_participant_rule",
            joinColumns = {
                    @JoinColumn(name = "processId", referencedColumnName = "processId"),
                    @JoinColumn(name = "title", referencedColumnName = "title")
            }
    )
    @MapKeyColumn(name = "participant_id")
    private Map<String, Rules> rules = new HashMap<>();
    public void addDocuments(Set<Document> documents) {
        
        this.documents.addAll(documents);
    }
    
    public String getProcessId() {
        
        return id.getProcessId();
    }
    
    public String getTitle() {
        
        return id.getTitle();
    }
    
    public void setProcessId(String processId) {
        
        id.setProcessId(processId);
    }
    
    public void setTitle(String title) {
        
        id.setTitle(title);
    }
}

