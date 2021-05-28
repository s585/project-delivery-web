package tech.itpark.project_delivery_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.itpark.project_delivery_web.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
