package tech.itpark.project_delivery_web.security.exeption;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenNotFoundException extends AuthenticationException{
    public JwtTokenNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtTokenNotFoundException(String msg) {
        super(msg);
    }
}
