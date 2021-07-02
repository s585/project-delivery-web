package tech.itpark.project_delivery_web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.RegistrationRequestDto;
import tech.itpark.project_delivery_web.dto.RegistrationResponseDto;
import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.service.user.UserService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PreAuthorize("hasAuthority('GET')")
    public void getAll(ServerRequest request, ServerResponse response) {
        final List<UserDto> users = service.findAll(request.getToken());
        response.write(users, ContentTypes.APPLICATION_JSON);
    }

    public void register(ServerRequest request, ServerResponse response) {
        final RegistrationResponseDto saved = service.register(request.read(RegistrationRequestDto.class));
        response.write(saved, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('GET')")
    public void getById(ServerRequest request, ServerResponse response) {
        final UserDto dto = service.findById(Long.valueOf(request.getRequestAttribute("id")), request.getToken());
        response.write(dto, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    public void setStatusActiveById(ServerRequest request, ServerResponse response) throws IOException {
        service.setStatusActiveById(Long.valueOf(request.getRequestAttribute("id")), request.getToken());
        response.write("User with id " + request.getRequestAttribute("id") + " has been brought back to life", ContentTypes.TEXT_PLAIN);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    public void deleteById(ServerRequest request, ServerResponse response) throws IOException {
        service.deleteById(Long.valueOf(request.getRequestAttribute("id")), request.getToken());
        response.write("User with id " + request.getRequestAttribute("id") + " has been deleted", ContentTypes.TEXT_PLAIN);
    }

}
