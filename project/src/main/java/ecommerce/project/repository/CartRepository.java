package ecommerce.project.repository;

import ecommerce.project.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_Id(Long userId);
}

