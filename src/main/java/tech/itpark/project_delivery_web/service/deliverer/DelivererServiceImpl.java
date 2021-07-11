package tech.itpark.project_delivery_web.service.deliverer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.itpark.project_delivery_web.dto.DelivererDto;
import tech.itpark.project_delivery_web.mappers.DelivererMapper;
import tech.itpark.project_delivery_web.model.enums.UserStatus;
import tech.itpark.project_delivery_web.model.user.Deliverer;
import tech.itpark.project_delivery_web.repository.DelivererRepository;
import tech.itpark.project_delivery_web.service.delivery.DeliveryService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DelivererServiceImpl implements DelivererService {

    private DelivererRepository delivererRepository;
    private DelivererMapper delivererMapper;
    private DeliveryService deliveryService;

    @Autowired
    public void setDelivererRepository(DelivererRepository delivererRepository) {
        this.delivererRepository = delivererRepository;
    }

    @Autowired
    public void setDelivererMapper(DelivererMapper delivererMapper) {
        this.delivererMapper = delivererMapper;
    }

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public List<DelivererDto> findAll() {
        return delivererRepository.findAll().stream().map(delivererMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public DelivererDto findById(Long id) {
        final Deliverer deliverer = delivererRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find deliverer by passed id: " + id));
        return delivererMapper.toDto(deliverer);
    }

    @Override
    public DelivererDto create(DelivererDto dto) {
        final Deliverer deliverer = delivererMapper.toEntity(dto);
        double[] coordinates = deliveryService.getCoordinates(dto.getAddress());
        deliverer.setLon(coordinates[0]);
        deliverer.setLat(coordinates[1]);
        final Deliverer saved = delivererRepository.save(deliverer);
        return delivererMapper.toDto(saved);
    }

    @Override
    public DelivererDto update(DelivererDto dto) {
        final Deliverer deliverer = delivererMapper.toEntity(dto);
        final Deliverer saved = delivererRepository.save(deliverer);
        return delivererMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id, String token) {
        final Deliverer deliverer = delivererRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by passed id: " + id));
        deliverer.setStatus(UserStatus.DELETED);
        delivererRepository.save(deliverer);
    }

    @Override
    public void setStatusActiveById(Long id) {
        final Deliverer deliverer = delivererRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by passed id: " + id));
        deliverer.setStatus(UserStatus.ACTIVE);
        delivererRepository.save(deliverer);
    }
}
