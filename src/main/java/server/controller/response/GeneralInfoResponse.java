package server.controller.response;

import lombok.Builder;
import lombok.Data;
import server.entity.process.Participant;
import server.entity.process.Process;

import java.util.Set;

@Builder
@Data
public class GeneralInfoResponse extends Response{
    
    public static final String SERVER_ERROR = "Error on server side";
    public static final String SUCCESS_LOADING = "Success info loading";
    
    private Participant participant;
    
    private Set<String> teams;
    
    private Set<Process> processes;
    
    public GeneralInfoResponse message(String message) {
        
        setMessage(message);
        
        return this;
    }
    
    public GeneralInfoResponse status(String status) {
        
        setStatus(status);
        
        return this;
    }
}
