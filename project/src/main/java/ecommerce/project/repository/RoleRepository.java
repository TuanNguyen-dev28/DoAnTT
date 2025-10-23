package ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.project.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
