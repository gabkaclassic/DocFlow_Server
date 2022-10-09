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
    
    public static final String SUCCESS_REGISTRATION = "Registration was finished with success status";
    public static final String USER_ALREADY_EXISTS = "Account with this login already exists";
    public static final String SUCCESS_LOGOUT = "Logout process finished with success status";
    public static final String WEAK_PASSWORD = "Password must contain 8 characters, at least 1 special symbol and at least 1 integer";
    public static final String INVALID_LOGIN = "Login must contain from 2 to 64 symbols";
    
    protected String status;
    
    protected String message;
    
    public boolean error() {
        return status.equals(STATUS_ERROR);
    }
}
