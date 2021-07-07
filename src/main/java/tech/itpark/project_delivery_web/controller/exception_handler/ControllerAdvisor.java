package tech.itpark.project_delivery_web.controller.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.itpark.project_delivery_web.exception.PermissionDeniedException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseBody> handleException(AccessDeniedException e) {
        final ResponseBody response = new ResponseBody(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseBody> handleException(UsernameNotFoundException e) {
        final ResponseBody response = new ResponseBody(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ResponseBody> handleException(PermissionDeniedException e) {
        final ResponseBody response = new ResponseBody(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseBody> handleException(BadCredentialsException e) {
        final ResponseBody response = new ResponseBody(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

