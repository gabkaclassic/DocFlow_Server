package server.entity.process.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "documents")
@NoArgsConstructor
@Data
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String title;
    
    @OneToMany
    private Set<Comment> comments;
    
    @Column
    @Lob
    private byte[] file;
    
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Resource> resources;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private DocumentType type;
}

