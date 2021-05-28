package tech.itpark.project_delivery_web.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.dto.CartDto;
import tech.itpark.project_delivery_web.dto.OrderDto;
import tech.itpark.project_delivery_web.model.Cart;
import tech.itpark.project_delivery_web.model.Order;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final ModelMapper modelMapper;

    public CartDto toDto(Cart entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, CartDto.class);
    }

    public Cart toEntity(CartDto dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, Cart.class);
    }
}
