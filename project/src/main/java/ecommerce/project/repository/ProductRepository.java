package ecommerce.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ecommerce.project.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByIsBestSellerTrue();
    List<Product> findByIsNewArrivalTrue();
    List<Product> findByIsLimitedEditionTrue();
}
