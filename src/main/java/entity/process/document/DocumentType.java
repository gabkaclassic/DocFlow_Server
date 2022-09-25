package entity.process.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "document_types")
@NoArgsConstructor
@Data
public class DocumentType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(
            unique = true
    )
    private String title;
    
    @Column
    private String defaultFormat;
    
}
