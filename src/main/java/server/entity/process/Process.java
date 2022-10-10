package server.entity.process;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "processes")
@NoArgsConstructor
@Data
public class Process {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<Step> steps;
    
    @OneToOne(fetch = FetchType.EAGER)
    private Step currentStep;
    
    public Step nextStep() {
        
        return currentStep = currentStep.getNextStep();
    }
}
