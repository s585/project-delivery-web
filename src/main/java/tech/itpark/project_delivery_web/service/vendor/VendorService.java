package tech.itpark.project_delivery_web.service.vendor;

import tech.itpark.project_delivery_web.dto.RegistrationResponseDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorRegistrationRequestDto;
import tech.itpark.project_delivery_web.model.user.Vendor;

import java.util.List;

public interface VendorService {

    List<VendorDto> findAll(String token);

    VendorDto findById(Long id, String token);

    Vendor findByEmail(String email);

    RegistrationResponseDto register(VendorRegistrationRequestDto read);

    VendorDto update(VendorDto dto);

    void deleteById(Long id, String token);

    void setStatusActiveById(Long id, String token);
}
