package tech.itpark.project_delivery_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.itpark.project_delivery_web.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);
}
