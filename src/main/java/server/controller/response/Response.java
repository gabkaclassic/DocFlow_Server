package server.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Родительский класс для всех типов ответов
 * @see ExistResponse
 * @see InfoResponse
 * @see ProcessResponse
 * @see StepResponse
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_SUCCESS = "success";
    public static final String SUCCESS_REFUSE = "Success refuse of invite";
    public static final String SERVER_ERROR = "Error on server side";
    public static final String SUCCESS_LOADING = "Success info loading";
    public static final String SUCCESS_CREATING = "Success creating process";
    public static final String SUCCESS_INVITE = "Success invite";
    
    public static final String SUCCESS_INVITE_REFUSE = "Success invite refuse";
    public static final String INVALID_LOGIN_PROCESS = "Invalid login or password";
    public static final String SUCCESS_REGISTRATION = "Registration was finished with success status";
    public static final String USER_ALREADY_EXISTS = "Account with this login already exists";
    public static final String SUCCESS_LOGOUT = "Logout process finished with success status";
    public static final String WEAK_PASSWORD = "Password must contain 8 characters, at least 1 special symbol and at least 1 integer";
    public static final String INVALID_LOGIN = "Login must contain from 2 to 64 symbols";
    public static final String NOT_EXIST = "Entity is not exists";
    
    protected String status;
    
    protected String message;
    
    @JsonIgnore
    public boolean isError() {
        return status.equals(STATUS_ERROR);
    }
    
    public static Response successResponse(String message) {
    
        var response = new Response();
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(message);
    
        return response;
    }
    
    public static Response errorResponse(String error) {
        
        var response = new Response();
        response.setStatus(Response.STATUS_ERROR);
        response.setMessage(error);
        
        return response;
    }
}
