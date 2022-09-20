package entity.process.document;

import javax.persistence.*;

@Entity
@Table(name = "resources")
public class Resource {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String value;
    
    @Column
    private String description;
}
