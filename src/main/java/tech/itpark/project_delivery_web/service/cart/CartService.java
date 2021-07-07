package tech.itpark.project_delivery_web.service.cart;

import tech.itpark.project_delivery_web.dto.CartDto;
import tech.itpark.project_delivery_web.dto.OrderDto;

public interface CartService {

    CartDto findByUserId(Long id, String token);

    CartDto findById(Long id, String token);

    CartDto create(CartDto dto, String token);

    OrderDto checkout(CartDto dto, String token);

    CartDto update(CartDto dto);

    void deleteById(Long id, String token);
}
