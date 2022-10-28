package server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import server.controller.response.Response;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response handleError(Exception exception) {
    
        
        log.warn(exception.getLocalizedMessage());
        
        return new Response(Response.STATUS_ERROR, exception.getMessage());
    }
}