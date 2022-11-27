package server.entity.process.document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import server.entity.deserializer.DocumentIdDeserializer;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * ID для сущности "Документ", состоящее из наименования шага, на котором документ был создан, и названия документа
 * */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
@EqualsAndHashCode
@JsonDeserialize(using = DocumentIdDeserializer.class)
public class DocumentId implements Serializable {

    private String stepTitle;
    
    private String title;
}
