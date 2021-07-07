package tech.itpark.project_delivery_web.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.itpark.project_delivery_web.dto.CartDto;
import tech.itpark.project_delivery_web.dto.OrderDto;
import tech.itpark.project_delivery_web.mappers.CartMapper;
import tech.itpark.project_delivery_web.mappers.OrderMapper;
import tech.itpark.project_delivery_web.model.Cart;
import tech.itpark.project_delivery_web.model.Order;
import tech.itpark.project_delivery_web.model.enums.CartStatus;
import tech.itpark.project_delivery_web.model.enums.OrderStatus;
import tech.itpark.project_delivery_web.repository.CartRepository;
import tech.itpark.project_delivery_web.repository.OrderRepository;
import tech.itpark.project_delivery_web.service.authentication.AuthenticationService;
import tech.itpark.project_delivery_web.service.order.OrderService;
import tech.itpark.project_delivery_web.service.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private AuthenticationService authenticationService;
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private UserService userService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CartDto findByUserId(Long id, String token) {
//        return cartRepository.findAll().stream().map(cartMapper::toDto).collect(Collectors.toList());
        return cartMapper.toDto(cartRepository.findByOwner(id));
    }

    @Override
    public CartDto findById(Long id, String token) {
        final Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart by passed id: " + id));
        return cartMapper.toDto(cart);
    }

    @Override
    public CartDto create(CartDto dto, String token) {
        Cart cart = cartMapper.toEntity(dto);
        cart.setOwner(userService.findByEmail(authenticationService.getEmail(token)));
        cart.setTotalPrice(cart.getProducts().stream().map(i -> i.getPrice()).mapToLong(Long::longValue).sum());
        cart.setCreationDate(LocalDateTime.now());
        cart.setCartStatus(CartStatus.UNPAID);
        final Cart saved = cartRepository.save(cart);
        return cartMapper.toDto(saved);
    }

    @Override
    public OrderDto checkout(CartDto dto, String token) {
        Order order = new Order();
        final Cart cart = cartMapper.toEntity(dto);
        order.setOwner(cart.getOwner());
//        order.setDeliverer();
//        order.setDeliveryPrice();
//        order.setTotalPrice();
        order.setCreationDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        final Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
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
