package server.controller.response;

import lombok.Builder;
import lombok.Data;

/**
 * Тип ответа для получения информации о наличии сущности в БД
 * @see Response
 * */
@Builder
@Data
public class ExistResponse extends Response {
    
    private boolean exist;
    public ExistResponse status(String status) {
        
        setStatus(status);
        
        return this;
    }
    
}
