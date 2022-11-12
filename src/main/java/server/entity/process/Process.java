package server.entity.process;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Step> steps;
    
    @Column
    private Integer currentStep;
}