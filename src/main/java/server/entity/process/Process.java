package server.entity.process;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.deserializer.ProcessDeserializer;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "processes")
@NoArgsConstructor
@Data
@JsonDeserialize(using = ProcessDeserializer.class)
public class Process {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String title;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Step> steps;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Step currentStep;
    
    public boolean nextStep() {
        
        return Objects.nonNull(currentStep = steps.stream()
                    .filter(step -> step.getNumber() == currentStep.getNumber() + 1)
                    .findFirst().orElse(null)
        );
        
    }
}