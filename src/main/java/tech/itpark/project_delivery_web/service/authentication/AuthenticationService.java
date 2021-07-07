package tech.itpark.project_delivery_web.service.authentication;

import org.springframework.security.core.Authentication;
import tech.itpark.project_delivery_web.dto.PasswordResetRequestDto;
import tech.itpark.project_delivery_web.dto.user.UserAuthDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorAuthDto;
import tech.itpark.project_delivery_web.model.Role;

import java.util.Date;
import java.util.Map;

public interface AuthenticationService {

    Map<String, Object> processRequest(UserAuthDto incomingData);

    Map<String, Object> processRequest(VendorAuthDto incomingData);

    String getEmail(String token);

    Authentication authenticate(String email, String password);

    String createToken(Role role, String email);

    void saveGeneratedJwtToken(String jwt, Date date, String status);

//    boolean recoverPassword(PasswordRecoverDto dto);

    boolean resetPassword(PasswordResetRequestDto dto);
}
