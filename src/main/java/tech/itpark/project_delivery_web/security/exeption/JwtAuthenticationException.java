package tech.itpark.project_delivery_web.security.exeption;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException{
    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
