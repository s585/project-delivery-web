package tech.itpark.project_delivery_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.itpark.project_delivery_web.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
