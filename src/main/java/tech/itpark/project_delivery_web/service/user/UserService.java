package tech.itpark.project_delivery_web.service.user;

import tech.itpark.project_delivery_web.dto.user.RegistrationRequestDto;
import tech.itpark.project_delivery_web.dto.RegistrationResponseDto;
import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.model.user.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Long id);

    User findByEmail(String email);

    RegistrationResponseDto register(RegistrationRequestDto dto);

    UserDto update(Long id, UserDto dto);

    User update(User user);

    void setStatusActiveById(Long id);

    void deleteById(Long id);
}
