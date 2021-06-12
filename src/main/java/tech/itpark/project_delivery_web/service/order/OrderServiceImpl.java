package tech.itpark.project_delivery_web.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.itpark.project_delivery_web.dto.OrderDto;
import tech.itpark.project_delivery_web.mappers.OrderMapper;
import tech.itpark.project_delivery_web.model.Order;
import tech.itpark.project_delivery_web.repository.OrderRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private OrderMapper orderMapper;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDto> findAll(String token) {
        return orderRepository.findAll().stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findAllByOwnerId(Long id, String token) {
        return orderRepository.findAllByOwner(id).stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(Long id, String token) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find vendor by passed id: " + id));
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto create(OrderDto dto) {
        final Order order = orderMapper.toEntity(dto);
        final Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }

    @Override
    public OrderDto update(OrderDto dto) {
        final Order order = orderMapper.toEntity(dto);
        final Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id, String token) {
        orderRepository.deleteById(id);
    }
}
