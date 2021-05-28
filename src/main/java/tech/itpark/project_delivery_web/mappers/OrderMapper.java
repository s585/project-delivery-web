package tech.itpark.project_delivery_web.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.dto.OrderDto;
import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.model.Order;
import tech.itpark.project_delivery_web.model.User;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderDto toDto(Order entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, OrderDto.class);
    }

    public Order toEntity(OrderDto dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, Order.class);
    }
}
