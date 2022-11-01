package server.entity.process.document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.deserializer.DocumentDeserializer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documents")
@NoArgsConstructor
@Data
@JsonDeserialize(using = DocumentDeserializer.class)
public class Document {
    
    @Id
    @Column
    private String title;
    
    @OneToMany(cascade = CascadeType.MERGE)
    private List<Comment> comments = new ArrayList<>();
    
    @Column
    @Lob
    private byte[] file;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Resource> resources = new ArrayList<>();
    
    @Column
    private String format;
}

