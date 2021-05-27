package tech.itpark.project_delivery_web.service.authentication;

import org.springframework.security.core.Authentication;
import tech.itpark.project_delivery_web.dto.user.UserDtoAuth;

import java.util.Date;
import java.util.Map;

public interface AuthenticationService {

    Map<String, Object> processRequest(UserDtoAuth incomingData);

    String getEmail(String token);

    Authentication authenticate(String email, String password);

    String createToken(Authentication authentication, String role, String email);

    void saveGeneratedJwtToken(String jwt, Date date, String status);
}
