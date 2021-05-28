package tech.itpark.project_delivery_web.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.dto.ProductDto;
import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.model.Product;
import tech.itpark.project_delivery_web.model.User;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductDto toDto(Product entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, ProductDto.class);
    }

    public Product toEntity(ProductDto dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, Product.class);
    }
}
