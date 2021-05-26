package tech.itpark.project_delivery_web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.GroupSaveRequestDto;
import tech.itpark.project_delivery_web.service.GroupService;

@Controller
@RequiredArgsConstructor
public class GroupController {
    private final GroupService service;

    public void save(ServerRequest request, ServerResponse response) {
        final var requestDto = request.read(GroupSaveRequestDto.class);
        final var responseDto = service.save(request.auth(), requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }
}
