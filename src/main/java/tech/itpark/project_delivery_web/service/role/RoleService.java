package tech.itpark.project_delivery_web.service.role;

import tech.itpark.project_delivery_web.dto.ProductDto;
import tech.itpark.project_delivery_web.dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> findAll(String token);

    RoleDto findById(Long id, String token);

    RoleDto create(RoleDto dto);

    RoleDto update(RoleDto dto);

    void deleteById(Long id, String token);
}
