package tech.itpark.project_delivery_web.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.PasswordResetRequestDto;
import tech.itpark.project_delivery_web.dto.user.UserDtoAuth;
import tech.itpark.project_delivery_web.service.authentication.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

@Getter
@Controller
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void login(ServerRequest request, ServerResponse response) {
        Map<String, Object> data = authenticationService.processRequest(request.read(UserDtoAuth.class));
        response.write(data, ContentTypes.APPLICATION_JSON);
    }

    public void logout(ServerRequest request, ServerResponse response) {
        response.write("logout success", ContentTypes.TEXT_PLAIN);
    }

    public void recoverPassword(ServerRequest request, ServerResponse response) {
        boolean res = authenticationService.resetPassword(request.read(PasswordResetRequestDto.class));
        Map<String, Boolean> data = new HashMap<>();
        data.put("Update password", res);
        response.write(data, ContentTypes.APPLICATION_JSON);
    }
}
