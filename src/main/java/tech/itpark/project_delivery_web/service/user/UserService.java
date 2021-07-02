package tech.itpark.project_delivery_web.service.user;

import tech.itpark.project_delivery_web.dto.RegistrationRequestDto;
import tech.itpark.project_delivery_web.dto.RegistrationResponseDto;
import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.dto.user.UserDtoRegistration;
import tech.itpark.project_delivery_web.model.user.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAll(String token);

    UserDto findById(Long id, String token);

//    UserDto create(UserDtoRegistration dto);

    RegistrationResponseDto register(RegistrationRequestDto dto);

    UserDto update(UserDto dto);

    User update(User user);

    void setStatusActiveById(Long id, String token);

    void deleteById(Long id, String token);

    User getByEmail(String email);
}
