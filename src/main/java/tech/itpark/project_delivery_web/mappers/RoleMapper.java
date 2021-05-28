package tech.itpark.project_delivery_web.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.dto.ProductDto;
import tech.itpark.project_delivery_web.dto.RoleDto;
import tech.itpark.project_delivery_web.model.Product;
import tech.itpark.project_delivery_web.model.Role;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleDto toDto(Role entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, RoleDto.class);
    }

    public Role toEntity(RoleDto dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, Role.class);
    }
}
