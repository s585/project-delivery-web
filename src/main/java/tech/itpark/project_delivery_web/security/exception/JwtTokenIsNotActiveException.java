package tech.itpark.project_delivery_web.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenIsNotActiveException extends AuthenticationException {
    public JwtTokenIsNotActiveException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtTokenIsNotActiveException(String msg) {
        super(msg);
    }
}
