package server.entity.process.document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.deserializer.DocumentDeserializer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "documents")
@NoArgsConstructor
@Data
@JsonDeserialize(using = DocumentDeserializer.class)
public class Document {
    
    @Id
    private String id;
    
    @Column
    private String title;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Comment> comments;
    
    @Column
    @Lob
    private byte[] file;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Resource> resources;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private DocumentType type;
}

