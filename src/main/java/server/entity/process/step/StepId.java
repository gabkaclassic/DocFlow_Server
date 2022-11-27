package server.entity.process.step;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import server.entity.deserializer.StepIdDeserializer;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * ID для сущности "Шаг", состоящее их наименования шага и ID процесса, в котором он был создан
 * */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
@JsonDeserialize(using = StepIdDeserializer.class)
public class StepId implements Serializable {
    
    private String processId;
    
    private String title;
}