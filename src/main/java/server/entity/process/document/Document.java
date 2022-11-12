package server.entity.process.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import server.entity.deserializer.DocumentDeserializer;

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
                    @JoinColumn(name = "stepTitle", referencedColumnName = "stepTitle"),
                    @JoinColumn(name = "title", referencedColumnName = "title")
            }
    )
    private List<String> comments = new ArrayList<>();
    
    @Column
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] file;
    
    @ElementCollection
    @CollectionTable(
            name = "documents_resources",
            joinColumns = {
                    @JoinColumn(name = "stepTitle", referencedColumnName = "stepTitle"),
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
    
    public void setTitle(String title) {
        
        id.setTitle(title);
    }
    
    public void setStepTitle(String title) {
        this.id.setStepTitle(title);
    }
}

