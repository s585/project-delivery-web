package tech.itpark.project_delivery_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.itpark.project_delivery_web.dto.OrderDto;
import tech.itpark.project_delivery_web.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOwner(Long id);
}
