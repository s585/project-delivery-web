package tech.itpark.project_delivery_web.service.order;

import tech.itpark.project_delivery_web.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> findAllByOwnerId(Long id);

    List<OrderDto> findAllByVendorId(Long id);

    OrderDto findById(Long id);

    OrderDto update(OrderDto dto);

    void deleteById(Long id);
}
