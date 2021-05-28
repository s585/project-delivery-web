package tech.itpark.project_delivery_web.service.vendor;

import tech.itpark.project_delivery_web.dto.ProductDto;
import tech.itpark.project_delivery_web.dto.VendorDto;

import java.util.List;

public interface VendorService {

    List<VendorDto> findAll(String token);

    VendorDto findById(Long id, String token);

    VendorDto create(VendorDto dto);

    VendorDto update(VendorDto dto);

    void deleteById(Long id, String token);
}
