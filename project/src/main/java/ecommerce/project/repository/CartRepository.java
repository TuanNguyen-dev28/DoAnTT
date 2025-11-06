package ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecommerce.project.entity.Cart;
import java.math.BigDecimal;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_Id(Long userId);
    
    @Modifying
    @Query("UPDATE Cart c SET c.totalItems = 0, c.totalPrice = :zero WHERE c.id = :cartId")
    void clearCartTotals(@Param("cartId") Long cartId, @Param("zero") BigDecimal zero);
}

