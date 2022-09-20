package entity.process.document;

import entity.user.Client;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String text;
    
    @Column
    private Client author;
}

