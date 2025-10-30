package ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.project.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

