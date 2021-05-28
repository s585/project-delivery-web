package tech.itpark.project_delivery_web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.itpark.project_delivery_web.dto.user.UserDtoAuth;
import tech.itpark.project_delivery_web.service.authentication.AuthenticationService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDtoAuth requestDto) {
        Map<String, Object> response = authenticationService.processRequest(requestDto);
        return ResponseEntity.ok(response);
    }
}
