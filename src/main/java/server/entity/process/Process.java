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
    private String id;
    
    @Column
    private String title;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Step> steps;
    
    @Column
    private Integer currentStep;
    
    public Step nextStep() {
        
        var curr = steps.stream()
                .filter(step -> Objects.equals(step.getNumber(), currentStep))
                .findFirst().orElse(null);
        var next = steps.stream()
                .filter(step -> step.getNumber() == currentStep + 1)
                .findFirst().orElse(null);
        
        if(next != null) {
            next.addDocuments(curr.getDocuments());
            currentStep = next.getNumber();
        }
        
        return next;
    }
    public Step previousStep() {
        currentStep = steps.stream()
                .map(Step::getNumber)
                .filter(n -> n == currentStep - 1)
                .findFirst().orElse(currentStep);
        
        return steps.stream()
                .filter(s -> Objects.equals(s.getNumber(), currentStep))
                .findFirst().orElse(null);
    }
    
    public boolean started() {
        return steps.stream()
                .noneMatch(step -> step.getNumber() == currentStep - 1);
    }
    public boolean finished() {
        
        return steps.stream()
                .noneMatch(step -> step.getNumber() == currentStep + 1);
    }
    
    public Step currentStep() {
        
        return steps.stream().filter(s -> Objects.equals(s.getNumber(), currentStep)).findFirst().get();
    }
}