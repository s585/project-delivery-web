package tech.itpark.project_delivery_web.service.vendor;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.itpark.project_delivery_web.dto.RegistrationResponseDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorRegistrationRequestDto;
import tech.itpark.project_delivery_web.mappers.VendorMapper;
import tech.itpark.project_delivery_web.model.Role;
import tech.itpark.project_delivery_web.model.enums.UserStatus;
import tech.itpark.project_delivery_web.model.user.Vendor;
import tech.itpark.project_delivery_web.repository.RoleRepository;
import tech.itpark.project_delivery_web.repository.VendorRepository;
import tech.itpark.project_delivery_web.service.authentication.AuthenticationService;
import tech.itpark.project_delivery_web.service.delivery.DeliveryService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {

    private AuthenticationService authenticationService;
    private BCryptPasswordEncoder passwordEncoder;
    private DeliveryService deliveryService;
    private RoleRepository roleRepository;
    private VendorRepository vendorRepository;
    private VendorMapper vendorMapper;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Autowired
    public void setVendorMapper(VendorMapper vendorMapper) {
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDto> findAll() {
        return vendorRepository.findAll().stream().map(vendorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public VendorDto findById(Long id) {
        final Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find vendor by passed id: " + id));
        return vendorMapper.toDto(vendor);
    }

    @Override
    public Vendor findByEmail(String email) {
        return vendorRepository.findByEmail(email);
    }

    public RegistrationResponseDto register(VendorRegistrationRequestDto dto) {
        Vendor vendor = vendorMapper.toEntity(dto);

        vendor.setPassword(passwordEncoder.encode(vendor.getPassword()));
        vendor.setSecret(passwordEncoder.encode(vendor.getSecret()));
        vendor.setStatus(UserStatus.ACTIVE);

        Role roleUser = roleRepository.findByName("VENDOR")
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        if (vendor.getRole() == null)
            vendor.setRole(roleUser);

        return vendorMapper.toRegistrationDto(vendorRepository.save(vendor));
    }

    @Override
    public VendorDto update(Long id, VendorDto dto) {
        Vendor persisted = vendorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find vendor by passed id: " + id));
        BeanUtils.copyProperties(dto, persisted, "id");
        double[] coordinates = deliveryService.getCoordinates(persisted.getAddress());
        persisted.setLon(coordinates[0]);
        persisted.setLat(coordinates[1]);
        final Vendor updated = vendorRepository.save(persisted);
        return vendorMapper.toDto(updated);
    }

    @Override
    public void setStatusActiveById(Long id) {
        final Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by passed id: " + id));
        vendor.setStatus(UserStatus.ACTIVE);
        vendorRepository.save(vendor);
    }

    @Override
    public void deleteById(Long id) {
        final Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by passed id: " + id));
        vendor.setStatus(UserStatus.DELETED);
        vendorRepository.save(vendor);
    }
}
