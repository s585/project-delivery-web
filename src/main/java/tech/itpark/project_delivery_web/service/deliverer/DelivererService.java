package tech.itpark.project_delivery_web.service.deliverer;

import tech.itpark.project_delivery_web.dto.DelivererDto;

import java.util.List;

public interface DelivererService {

    List<DelivererDto> findAll();

    DelivererDto findById(Long id);

    DelivererDto create(DelivererDto dto);

    DelivererDto update(Long id, DelivererDto dto);

    void deleteById(Long id, String token);

    void setStatusActiveById(Long id);
}
