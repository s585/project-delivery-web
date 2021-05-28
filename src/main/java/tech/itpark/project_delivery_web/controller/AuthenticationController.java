package tech.itpark.project_delivery_web.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.user.UserDtoAuth;
import tech.itpark.project_delivery_web.service.authentication.AuthenticationService;

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
}
