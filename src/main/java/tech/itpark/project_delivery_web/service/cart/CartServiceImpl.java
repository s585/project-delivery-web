package tech.itpark.project_delivery_web.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.itpark.project_delivery_web.dto.CartDto;
import tech.itpark.project_delivery_web.mappers.CartMapper;
import tech.itpark.project_delivery_web.model.Cart;
import tech.itpark.project_delivery_web.model.User;
import tech.itpark.project_delivery_web.repository.CartRepository;
import tech.itpark.project_delivery_web.service.authentication.AuthenticationService;
import tech.itpark.project_delivery_web.service.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    private CartMapper cartMapper;

    private UserService userService;

    private AuthenticationService authenticationService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public List<CartDto> findAll(String token) {
        return cartRepository.findAll().stream().map(cartMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CartDto findById(Long id, String token) {
        final Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find vendor by passed id: " + id));
        return cartMapper.toDto(cart);
    }

    @Override
    public CartDto create(CartDto dto) {
        final Cart cart = cartMapper.toEntity(dto);
        final Cart saved = cartRepository.save(cart);
        return cartMapper.toDto(saved);
    }

    @Override
    public CartDto update(CartDto dto) {
        final Cart cart = cartMapper.toEntity(dto);
        final Cart saved = cartRepository.save(cart);
        return cartMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id, String token) {
        cartRepository.deleteById(id);
    }
}
