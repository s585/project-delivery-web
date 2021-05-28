package tech.itpark.project_delivery_web.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.dto.DelivererDto;
import tech.itpark.project_delivery_web.model.Deliverer;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DelivererMapper {

    private final ModelMapper modelMapper;

    public DelivererDto toDto(Deliverer entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, DelivererDto.class);
    }

    public Deliverer toEntity(DelivererDto dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, Deliverer.class);
    }
}
