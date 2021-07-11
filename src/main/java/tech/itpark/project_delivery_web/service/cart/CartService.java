package tech.itpark.project_delivery_web.service.cart;

import tech.itpark.project_delivery_web.dto.CartDto;
import tech.itpark.project_delivery_web.dto.OrderDto;

public interface CartService {

    CartDto findByUserId(Long id);

    CartDto findById(Long id);

    CartDto create(CartDto dto, String token);

    OrderDto checkout(CartDto dto);

    CartDto update(CartDto dto);

    void deleteById(Long id);
}
