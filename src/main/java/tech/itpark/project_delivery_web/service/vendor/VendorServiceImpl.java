package tech.itpark.project_delivery_web.service.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.itpark.project_delivery_web.dto.VendorDto;
import tech.itpark.project_delivery_web.mappers.VendorMapper;
import tech.itpark.project_delivery_web.model.Vendor;
import tech.itpark.project_delivery_web.repository.VendorRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private VendorRepository vendorRepository;

    private VendorMapper vendorMapper;

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Autowired
    public void setVendorMapper(VendorMapper vendorMapper) {
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDto> findAll(String token) {
        return vendorRepository.findAll().stream().map(vendorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public VendorDto findById(Long id, String token) {
        final Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find vendor by passed id: " + id));
        return vendorMapper.toDto(vendor);
    }

    @Override
    public VendorDto create(VendorDto dto) {
        final Vendor vendor = vendorMapper.toEntity(dto);
        final Vendor saved = vendorRepository.save(vendor);
        return vendorMapper.toDto(saved);
    }

    @Override
    public VendorDto update(VendorDto dto) {
        final Vendor vendor = vendorMapper.toEntity(dto);
        final Vendor saved = vendorRepository.save(vendor);
        return vendorMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id, String token) {
        vendorRepository.deleteById(id);
    }
}
