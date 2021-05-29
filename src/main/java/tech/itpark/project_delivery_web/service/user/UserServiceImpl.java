package tech.itpark.project_delivery_web.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.dto.user.UserDtoRegistration;
import tech.itpark.project_delivery_web.mappers.UserMapper;
import tech.itpark.project_delivery_web.model.User;
import tech.itpark.project_delivery_web.model.enums.UserStatus;
import tech.itpark.project_delivery_web.repository.UserRepository;
import tech.itpark.project_delivery_web.service.authentication.AuthenticationService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private AuthenticationService authenticationService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> findAll(String token) {
        String email = authenticationService.getEmail(token);
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id, String token) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by passed id: " + id));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto create(UserDtoRegistration dto) {
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSecret(passwordEncoder.encode(user.getSecret()));
        user.setStatus(UserStatus.ACTIVE);
        final User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }


    @Override
    public UserDto update(UserDto dto) {
        final User user = userMapper.toEntity(dto);
        final User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id, String token) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }
    //    private final UserRepositoryImpl repository;
//    private final PasswordHasher passwordHasher;
//    private final TokenGenerator tokenGenerator;
//
//    public List<User> getAll(Auth auth) {
//        // authorization
//        if (!auth.hasRole("ROLE_ADMIN")) {
//            // InsufficientPrivilegesException
//            throw new PermissionDeniedException();
//        }
//
//        return repository.getAll();
//    }
//
//    public Optional<User> getById() {
//        return Optional.empty();
//    }
//
//    public User save(User user) {
//        return user;
//    }
//
//    public void deleteById(long id) {
//    }
//
//    public RegistrationResponseDto register(RegistrationRequestDto request) {
//        // --1. Свободен ли логин
//        // 2. Длина пароля и т.д. и т.п.
//        // 3. Хеш пароля
//        // -> 4. Сохранение в БД
//        // TODO: Чистота данных
//        // TODO: " admin ", ADMIN, aDmIN
//        // TODO: sanitizing (очистка данных) <- bad idea
//        // TODO: pattern matching (whitelist/allowlist)
//        // TODO: abcdef...0-9 (best practice)
//        // Regexp:
//        // TODO: https://regex101.com/
//        // TODO: ^ смотрим с начала строки
//        // TODO: $ смотрим до конца строки
//        // TODO: ^admin$
//        // TODO: [abc...zA...Z0...9]
//        // TODO: [a-zA-Z0-9]
//        // TODO: квантификаторы:
//        // TODO: ? - 0-1 символ
//        // TODO: * - 0+ символ
//        // TODO: + - 1+ символ
//        // TODO: {min}, {min, max}
//        if (request.getLogin() == null) {
//            throw new RuntimeException("login can't be null");
//        }
//
//        if (!request.getLogin().matches("^[a-z0-9]{5,10}$")) {
//            throw new RuntimeException("bad login");
////      throw new BadLoginException();
//        }
//
//        if (request.getPassword() == null) {
//            throw new RuntimeException("password can't be null");
//        }
//
//        if (request.getPassword().length() < 8) {
//            throw new RuntimeException("minimal length of password must be greater than 8");
//        }
//
//        // hash - ???
//        // TODO: 1. солить и хешировать +
//        // TODO: 2. blacklist простых паролей
//
//        final var hash = passwordHasher.hash(request.getPassword());
//
//        // register
//        final var saved = repository.save(
//                new User(0L, request.getLogin(), hash, request.getSecret(), new Role("ROLE_USER"))
//        );
//        return new RegistrationResponseDto(
//                saved.getId()
//        );
//    }
//
//    public LoginResponseDto login(LoginRequestDto request) {
//        final var user = repository.getByLogin(request.getLogin())
//                .orElseThrow(() -> new RuntimeException("user with such login didn't found"));
//
//        if (!passwordHasher.matches(user.getHash(), request.getPassword())) {
//            throw new RuntimeException("passwords not match");
//        }
//
//        final var token = tokenGenerator.generate();
//        // UserService.login
//        repository.save(new TokenAuth(user.getId(), token));
//        return new LoginResponseDto(token);
//    }
//
//    @Override
//    public Auth provide(String token) {
//        // BL -> Optional.empty -> Anonymous
//        return repository.getByToken(token)
//                .map(o -> (Auth) o)
//                .orElse(Auth.anonymous())
//                ;
//    }
//
//    public PasswordResetResponseDto resetPassword(PasswordResetRequestDto requestDto) {
//        final User user = repository.getByLogin(requestDto.getLogin())
//                .orElseThrow(() -> new RuntimeException("user with such login didn't found"));
//        if (!requestDto.getSecret().equals(user.getSecret())) {
//            throw new PermissionDeniedException("you shall not pass");
//        }
//        final var hash = passwordHasher.hash(requestDto.getPassword());
//        user.setHash(hash);
//        repository.updatePassword(user);
//        return new PasswordResetResponseDto(user.getId());
//    }
}
