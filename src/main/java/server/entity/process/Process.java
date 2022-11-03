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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String title;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Step> steps;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Step currentStep;
    
    public void nextStep() {
        
        var next = steps.stream()
                .filter(step -> step.getNumber() == currentStep.getNumber() + 1)
                .findFirst().orElse(null);
        
        if(next == null)
            return;
        
        next.addDocuments(currentStep.getDocuments());
        currentStep = next;
    }
    public void previousStep() {
        currentStep = steps.stream()
                .filter(step -> step.getNumber() == currentStep.getNumber() - 1)
                .findFirst().orElse(null);
    }
    
    public boolean started() {
        return steps.stream()
                .noneMatch(step -> step.getNumber() == currentStep.getNumber() - 1);
    }
    public boolean finished() {
        
        return steps.stream()
                .noneMatch(step -> step.getNumber() == currentStep.getNumber() + 1);
    }
}