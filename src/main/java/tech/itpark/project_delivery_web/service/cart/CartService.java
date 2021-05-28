package tech.itpark.project_delivery_web.service.cart;

import tech.itpark.project_delivery_web.dto.CartDto;

import java.util.List;

public interface CartService {

    List<CartDto> findAll(String token);

    CartDto findById(Long id, String token);

    CartDto create(CartDto dto);

    CartDto update(CartDto dto);

    void deleteById(Long id, String token);
}
