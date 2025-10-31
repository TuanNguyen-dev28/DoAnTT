package ecommerce.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ecommerce.project.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByIsBestSellerTrue();
    List<Product> findByIsNewArrivalTrue();
    List<Product> findByIsLimitedEditionTrue();

    // --- BẮT ĐẦU: THÊM PHƯƠNG THỨC TÌM SẢN PHẨM LIÊN QUAN ---
    // Tìm 4 sản phẩm cùng category, trừ sản phẩm đang xem
    List<Product> findTop4ByCategoryAndIdNot(String category, Long id);
    // --- KẾT THÚC ---
}
