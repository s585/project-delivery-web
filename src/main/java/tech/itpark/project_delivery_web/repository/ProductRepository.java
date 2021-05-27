package tech.itpark.project_delivery_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.itpark.project_delivery_web.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
