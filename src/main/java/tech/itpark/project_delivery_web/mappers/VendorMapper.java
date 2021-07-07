package tech.itpark.project_delivery_web.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.dto.RegistrationResponseDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorRegistrationRequestDto;
import tech.itpark.project_delivery_web.model.user.Vendor;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class VendorMapper {

    private final ModelMapper modelMapper;

    public VendorDto toDto(Vendor entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, VendorDto.class);
    }

    public Vendor toEntity(VendorDto dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, Vendor.class);
    }

    public Vendor toEntity(VendorRegistrationRequestDto dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, Vendor.class);
    }

    public RegistrationResponseDto toRegistrationDto(Vendor entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, RegistrationResponseDto.class);
    }
}
