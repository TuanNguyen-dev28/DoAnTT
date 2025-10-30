package ecommerce.project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.project.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
