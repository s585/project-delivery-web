package tech.itpark.project_delivery_web.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.itpark.project_delivery_web.dto.RoleDto;
import tech.itpark.project_delivery_web.mappers.RoleMapper;
import tech.itpark.project_delivery_web.model.Role;
import tech.itpark.project_delivery_web.repository.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    private RoleMapper roleMapper;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDto> findAll(String token) {
        return roleRepository.findAll().stream().map(roleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public RoleDto findById(Long id, String token) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find product by passed id: " + id));
        return roleMapper.toDto(role);
    }

    @Override
    public RoleDto create(RoleDto dto) {
        final Role role = roleMapper.toEntity(dto);
        final Role saved = roleRepository.save(role);
        return roleMapper.toDto(saved);
    }

    @Override
    public RoleDto update(RoleDto dto) {
        final Role role = roleMapper.toEntity(dto);
        final Role saved = roleRepository.save(role);
        return roleMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id, String token) {
        roleRepository.deleteById(id);
    }
}
