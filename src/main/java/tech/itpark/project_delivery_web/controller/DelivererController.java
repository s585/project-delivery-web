package tech.itpark.project_delivery_web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.DelivererDto;
import tech.itpark.project_delivery_web.dto.RegistrationResponseDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorRegistrationRequestDto;
import tech.itpark.project_delivery_web.service.deliverer.DelivererService;
import tech.itpark.project_delivery_web.service.vendor.VendorService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DelivererController {

    private final DelivererService delivererService;

    @PreAuthorize("hasAuthority('GET_USERS')")
    public void getAll(ServerRequest request, ServerResponse response) {
        final List<DelivererDto> deliverers = delivererService.findAll();
        response.write(deliverers, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('CREATE_USERS')")
    public void save(ServerRequest request, ServerResponse response) {
        final DelivererDto saved = delivererService.create(request.read(DelivererDto.class));
        response.write(saved, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('UPDATE_USER_INFO')")
    public void update(ServerRequest request, ServerResponse response) {
        Long id = Long.valueOf(request.getRequestAttribute("id"));
        final DelivererDto updated = delivererService.update(id, request.read(DelivererDto.class));
        response.write(updated, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('GET_USERS')")
    public void getById(ServerRequest request, ServerResponse response) {
        final DelivererDto dto = delivererService.findById(Long.valueOf(request.getRequestAttribute("id")));
        response.write(dto, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('SET_USER_STATUS_ACTIVE')")
    public void setStatusActiveById(ServerRequest request, ServerResponse response) throws IOException {
        delivererService.setStatusActiveById(Long.valueOf(request.getRequestAttribute("id")));
        response.write("User with id " + request.getRequestAttribute("id") + " has been brought back to life", ContentTypes.TEXT_PLAIN);
    }

    @PreAuthorize("hasAuthority('DELETE_USER_BY_ID')")
    public void deleteById(ServerRequest request, ServerResponse response) throws IOException {
        delivererService.deleteById(Long.valueOf(request.getRequestAttribute("id")), request.getToken());
        response.write("User with id " + request.getRequestAttribute("id") + " has been deleted", ContentTypes.TEXT_PLAIN);
    }

}
