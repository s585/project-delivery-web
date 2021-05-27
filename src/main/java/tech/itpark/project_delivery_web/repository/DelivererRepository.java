package tech.itpark.project_delivery_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.itpark.project_delivery_web.model.Deliverer;

public interface DelivererRepository extends JpaRepository<Deliverer, Long> {
}
