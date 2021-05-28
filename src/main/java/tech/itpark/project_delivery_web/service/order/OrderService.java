package tech.itpark.project_delivery_web.service.order;

import tech.itpark.project_delivery_web.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> findAll(String token);

    List<OrderDto> findAllByOwnerId(Long id, String token);

    OrderDto findById(Long id, String token);

    OrderDto create(OrderDto dto);

    OrderDto update(OrderDto dto);

    void deleteById(Long id, String token);
}
