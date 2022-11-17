package server.entity.process;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import server.entity.deserializer.ProcessDeserializer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "processes")
@NoArgsConstructor
@Data
@JsonDeserialize(using = ProcessDeserializer.class)
public class Process {
    
    @Id
    private String id;
    
    @Column
    private String title;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Step> steps;
    
    @Column
    private Integer currentStep;
}