package tech.itpark.project_delivery_web.service.user;

import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.dto.user.UserDtoRegistration;
import tech.itpark.project_delivery_web.model.user.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAll(String token);

    UserDto findById(Long id, String token);

    UserDto create(UserDtoRegistration dto);

    UserDto register(UserDtoRegistration dto);

    UserDto update(UserDto dto);

    User update(User user);

    void deleteById(Long id, String token);

    User getByEmail(String email);
}
