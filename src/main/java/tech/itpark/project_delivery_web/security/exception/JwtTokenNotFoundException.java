package tech.itpark.project_delivery_web.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenNotFoundException extends AuthenticationException {

    public JwtTokenNotFoundException(String message) {
        super(message);
    }
}
