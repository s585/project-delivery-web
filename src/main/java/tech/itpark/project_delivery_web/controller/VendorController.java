package tech.itpark.project_delivery_web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.*;
import tech.itpark.project_delivery_web.dto.vendor.VendorDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorRegistrationRequestDto;
import tech.itpark.project_delivery_web.service.vendor.VendorService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PreAuthorize("hasAuthority('GET_USERS')")
    public void getAll(ServerRequest request, ServerResponse response) {
        final List<VendorDto> vendors = vendorService.findAll();
        response.write(vendors, ContentTypes.APPLICATION_JSON);
    }

    public void register(ServerRequest request, ServerResponse response) {
        final RegistrationResponseDto saved = vendorService.register(request.read(VendorRegistrationRequestDto.class));
        response.write(saved, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('UPDATE_USER_INFO')")
    public void update(ServerRequest request, ServerResponse response) {
        Long id = Long.valueOf(request.getRequestAttribute("id"));
        final VendorDto updated = vendorService.update(id, request.read(VendorDto.class));
        response.write(updated, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('GET_USERS')")
    public void getById(ServerRequest request, ServerResponse response) {
        final VendorDto dto = vendorService.findById(Long.valueOf(request.getRequestAttribute("id")));
        response.write(dto, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('SET_USER_STATUS_ACTIVE')")
    public void setStatusActiveById(ServerRequest request, ServerResponse response) throws IOException {
        vendorService.setStatusActiveById(Long.valueOf(request.getRequestAttribute("id")));
        response.write("User with id " + request.getRequestAttribute("id") + " has been brought back to life", ContentTypes.TEXT_PLAIN);
    }

    @PreAuthorize("hasAuthority('DELETE_USER_BY_ID')")
    public void deleteById(ServerRequest request, ServerResponse response) throws IOException {
        vendorService.deleteById(Long.valueOf(request.getRequestAttribute("id")));
        response.write("User with id " + request.getRequestAttribute("id") + " has been deleted", ContentTypes.TEXT_PLAIN);
    }

}
