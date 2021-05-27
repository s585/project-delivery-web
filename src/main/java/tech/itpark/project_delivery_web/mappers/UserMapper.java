package tech.itpark.project_delivery_web.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.model.User;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDto toDto(User entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, UserDto.class);
    }

    public User toEntity(UserDto dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, User.class);
    }
}
