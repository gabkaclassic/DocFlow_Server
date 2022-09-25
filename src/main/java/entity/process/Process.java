package entity.process;

import entity.process.document.Document;
import entity.user.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "processes")
@NoArgsConstructor
@Data
public class Process {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany
    @JoinTable(
            name = "process_steps"
    )
    private List<Step> steps;
}
