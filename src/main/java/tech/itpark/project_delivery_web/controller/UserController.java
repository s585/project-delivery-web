package tech.itpark.project_delivery_web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.user.RegistrationRequestDto;
import tech.itpark.project_delivery_web.dto.RegistrationResponseDto;
import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.service.user.UserService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('GET_USERS')")
    public void getAll(ServerRequest request, ServerResponse response) {
        final List<UserDto> users = userService.findAll();
        response.write(users, ContentTypes.APPLICATION_JSON);
    }

    public void register(ServerRequest request, ServerResponse response) {
        final RegistrationResponseDto saved = userService.register(request.read(RegistrationRequestDto.class));
        response.write(saved, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('UPDATE_USER_INFO')")
    public void update(ServerRequest request, ServerResponse response) {
        Long id = Long.valueOf(request.getRequestAttribute("id"));
        final UserDto updated = userService.update(id, request.read(UserDto.class));
        response.write(updated, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('GET_USERS')")
    public void getById(ServerRequest request, ServerResponse response) {
        final UserDto dto = userService.findById(Long.valueOf(request.getRequestAttribute("id")));
        response.write(dto, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('SET_STATUS_ACTIVE')")
    public void setStatusActiveById(ServerRequest request, ServerResponse response) throws IOException {
        userService.setStatusActiveById(Long.valueOf(request.getRequestAttribute("id")));
        response.write("User with id " + request.getRequestAttribute("id") + " has been brought back to life", ContentTypes.TEXT_PLAIN);
    }

    @PreAuthorize("hasAuthority('DELETE_USER_BY_ID')")
    public void deleteById(ServerRequest request, ServerResponse response) throws IOException {
        userService.deleteById(Long.valueOf(request.getRequestAttribute("id")));
        response.write("User with id " + request.getRequestAttribute("id") + " has been deleted", ContentTypes.TEXT_PLAIN);
    }
}
