package server.controller.response;

import lombok.Builder;
import lombok.Data;
import server.entity.process.Process;

@Data
@Builder
public class ProcessResponse extends Response {
    
    private Process process;
    
    public ProcessResponse message(String message) {
        
        setMessage(message);
        
        return this;
    }
    
    public ProcessResponse status(String status) {
        
        setStatus(status);
        
        return this;
    }
    
}
