package tech.itpark.project_delivery_web.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.itpark.project_delivery_web.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p.* FROM products p INNER JOIN vendors_products vp ON p.id = vp.products_id WHERE vp.vendor_id = :vendorId ", nativeQuery = true)
    List<Product> findAllByVendorIdNative(@Param("vendorId") Long vendorId);

}
