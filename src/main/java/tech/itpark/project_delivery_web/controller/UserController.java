package tech.itpark.project_delivery_web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.LoginRequestDto;
import tech.itpark.project_delivery_web.dto.PasswordResetRequestDto;
import tech.itpark.project_delivery_web.dto.RegistrationRequestDto;
import tech.itpark.project_delivery_web.model.User;
import tech.itpark.project_delivery_web.service.UserService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    public void getAll(ServerRequest request, ServerResponse response) {
        // можем доставать auth только из request <- ThreadLocal
        // composable software
        final var data = service.findAll(request.getToken());
        response.write(data, ContentTypes.APPLICATION_JSON);
    }

    public void getById(ServerRequest request, ServerResponse response) {
//        service.findById()
    }
/*
    // users -> ADMIN
    public void getAll(ServerRequest request, ServerResponse response) throws IOException {
        // можем доставать auth только из request <- ThreadLocal
        // composable software
        final var auth = request.auth();
        final var data = service.getAll(auth);
        response.write(data, ContentTypes.APPLICATION_JSON);
    }

    public void getById(ServerRequest request, ServerResponse response) throws IOException {
        response.write("getById", ContentTypes.TEXT_PLAIN);
    }

    public void save(ServerRequest request, ServerResponse response) throws IOException {
        // 1. Подготовка данных для сервиса
        // 2. Вызов сервиса
        // 3. Обработка ответа
        final var dto = request.read(User.class);
        final var saved = service.save(dto);
        response.write(saved, ContentTypes.APPLICATION_JSON);
    }

    public void deleteById(ServerRequest request, ServerResponse response) throws IOException {
        response.write("deleteById", ContentTypes.TEXT_PLAIN);
    }

    public void register(ServerRequest request, ServerResponse response) throws IOException {
        final var requestDto = request.read(RegistrationRequestDto.class);
        final var responseDto = service.register(requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }

    public void passwordReset(ServerRequest request, ServerResponse response) throws IOException {
        final var requestDto = request.read(PasswordResetRequestDto.class);
        final var responseDto = service.resetPassword(requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }

    public void login(ServerRequest request, ServerResponse response) throws IOException {
        final var requestDto = request.read(LoginRequestDto.class);
        final var responseDto = service.login(requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }*/


}
