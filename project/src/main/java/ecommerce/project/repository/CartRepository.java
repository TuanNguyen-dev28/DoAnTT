package ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.project.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_Id(Long userId);
}

