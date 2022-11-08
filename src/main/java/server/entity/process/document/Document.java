package server.entity.process.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.deserializer.DocumentDeserializer;
import server.entity.process.Participant;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@JsonDeserialize(using = DocumentDeserializer.class)
public class Document {
    
    @EmbeddedId
    private DocumentId id;
    
    @ElementCollection
    @CollectionTable(
            name = "documents_comments",
            joinColumns = {
                    @JoinColumn(name = "processId", referencedColumnName = "processId"),
                    @JoinColumn(name = "title", referencedColumnName = "title")
            }
    )
    private List<String> comments = new ArrayList<>();
    
    @Column
    @Lob
    private byte[] file;
    
    @ElementCollection
    @CollectionTable(
            name = "documents_resources",
            joinColumns = {
                    @JoinColumn(name = "processId", referencedColumnName = "processId"),
                    @JoinColumn(name = "title", referencedColumnName = "title")
            }
    )
    private List<String> resources = new ArrayList<>();
    
    @Column
    private String format;
    
    @JsonIgnore
    public String getTitle() {
        
        return id.getTitle();
    }
    
    public String getProcessId() {
        
        return id.getProcessId();
    }
    
    public void addComment(String text, Participant author) {
        
        comments.add(String.format("--| From %s |-- %s", author.getUsername(), text));
    }
    
    public void addResource(String value, String description) {
        
        resources.add(String.format("%s: %s", description, value));
    }
    
    public void setTitle(String title) {
        
        id.setTitle(title);
    }
    
    public void addComments(List<String> comments) {
        
        this.comments.addAll(comments);
    }
    
    public void addResources(List<String> resources) {
        
        this.resources.addAll(resources);
    }
    
    public void setProcessId(String id) {
        this.id.setProcessId(id);
    }
}

