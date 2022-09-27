package controller.response;

import entity.process.Participant;
import entity.process.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepResponse {
    
    private Step step;
    
    private Participant participant;

}
