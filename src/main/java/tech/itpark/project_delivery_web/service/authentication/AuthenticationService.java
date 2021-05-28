package tech.itpark.project_delivery_web.service.authentication;

import org.springframework.security.core.Authentication;
import tech.itpark.project_delivery_web.dto.user.PasswordRecoverDto;
import tech.itpark.project_delivery_web.dto.user.UserDtoAuth;
import tech.itpark.project_delivery_web.model.Role;

import java.util.Date;
import java.util.Map;

public interface AuthenticationService {

    Map<String, Object> processRequest(UserDtoAuth incomingData);

    String getEmail(String token);

    Authentication authenticate(String email, String password);

    String createToken(Authentication authentication, Role role, String email);

    void saveGeneratedJwtToken(String jwt, Date date, String status);

    boolean recoverPassword(PasswordRecoverDto dto);
}
