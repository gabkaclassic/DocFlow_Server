package entity.process.document;

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
    @JoinTable(
            name = "document_comments"
    )
    private Set<Comment> comments;
    
    @Column
    private String file;
    
    @OneToMany
    @JoinTable(
            name = "document_resources"
    )
    private Set<Resource> resources;
    
    @ManyToOne
    private DocumentType type;
}
