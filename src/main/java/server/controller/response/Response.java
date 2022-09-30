package server.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_SUCCESS = "success";
    
    protected String status;
    
    protected String message;
}
