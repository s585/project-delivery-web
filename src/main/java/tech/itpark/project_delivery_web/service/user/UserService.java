package tech.itpark.project_delivery_web.service.user;

import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.model.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAll(String token);

    UserDto findById(Long id, String token);

    UserDto create(UserDto dto);

    UserDto update(UserDto dto);

    void deleteById(Long id, String token);

    User getByEmail(String email);
}
