package ecommerce.project.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ecommerce.project.entity.Product;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    Page<Product> findByCategory(String category, Pageable pageable);
    
    List<Product> findByIsBestSellerTrue();
    Page<Product> findByIsBestSellerTrue(Pageable pageable);
    
    List<Product> findByIsNewArrivalTrue();
    Page<Product> findByIsNewArrivalTrue(Pageable pageable);
    
    List<Product> findByIsLimitedEditionTrue();
    Page<Product> findByIsLimitedEditionTrue(Pageable pageable);

    // --- BẮT ĐẦU: THÊM PHƯƠNG THỨC TÌM SẢN PHẨM LIÊN QUAN ---
    // Tìm 4 sản phẩm cùng category, trừ sản phẩm đang xem
    List<Product> findTop4ByCategoryAndIdNot(String category, Long id);
    // --- KẾT THÚC ---

    @Query("SELECT p FROM Product p WHERE (p.name LIKE '%Men%' OR p.name LIKE '%Shirt%' OR p.name LIKE '%Jeans%' OR p.name LIKE '%Pants%' OR p.name LIKE '%Polo%') AND p.imageUrl IS NOT NULL AND p.imageUrl <> ''")
    List<Product> findMenProducts();
    
    @Query("SELECT p FROM Product p WHERE (p.name LIKE '%Men%' OR p.name LIKE '%Shirt%' OR p.name LIKE '%Jeans%' OR p.name LIKE '%Pants%' OR p.name LIKE '%Polo%') AND p.imageUrl IS NOT NULL AND p.imageUrl <> ''")
    Page<Product> findMenProducts(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (p.name LIKE '%onepiece%' OR p.name LIKE '%sweater%' OR p.name LIKE '%florish%' OR p.name LIKE '%Baggy%' OR p.name LIKE '%Dress%' OR p.name LIKE '%Jeans%' OR p.name LIKE '%Blouse%' OR p.name LIKE '%Skirt%' OR p.name LIKE '%Cardigan%' OR p.name LIKE '%Jumpsuit%') AND p.imageUrl IS NOT NULL AND p.imageUrl <> ''")
    List<Product> findWomenProducts();
    
    @Query("SELECT p FROM Product p WHERE (p.name LIKE '%onepiece%' OR p.name LIKE '%sweater%' OR p.name LIKE '%florish%' OR p.name LIKE '%Baggy%' OR p.name LIKE '%Dress%' OR p.name LIKE '%Jeans%' OR p.name LIKE '%Blouse%' OR p.name LIKE '%Skirt%' OR p.name LIKE '%Cardigan%' OR p.name LIKE '%Jumpsuit%') AND p.imageUrl IS NOT NULL AND p.imageUrl <> ''")
    Page<Product> findWomenProducts(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.season = :season AND p.imageUrl IS NOT NULL AND p.imageUrl <> ''")
    List<Product> findBySeason(String season);

    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);
}
