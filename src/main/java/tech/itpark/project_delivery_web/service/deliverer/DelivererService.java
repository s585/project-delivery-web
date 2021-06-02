package tech.itpark.project_delivery_web.service.deliverer;

import tech.itpark.project_delivery_web.dto.DelivererDto;

import java.util.List;

public interface DelivererService {

    List<DelivererDto> findAll(String token);

    DelivererDto findById(Long id, String token);

    DelivererDto create(DelivererDto dto);

    DelivererDto update(DelivererDto dto);

    void deleteById(Long id, String token);
}