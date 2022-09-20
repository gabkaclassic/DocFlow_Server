package entity.process.document;

import javax.persistence.*;
import java.io.File;
import java.util.Set;

@Entity
@Table(name = "documents")
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String title;
    
    @OneToMany
    @JoinTable(
            name = "document_comments"
    )
    private Set<Comment> comments;
    
    @Column
    private File file;
    
    @OneToMany
    @JoinTable(
            name = "document_resources"
    )
    private Set<Resource> resources;
    
    @Enumerated(EnumType.STRING)
    private DocumentType type;
}
