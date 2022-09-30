package server.entity.process.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.process.Participant;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Data
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;
    
    @ManyToOne
    private Participant author;
}

