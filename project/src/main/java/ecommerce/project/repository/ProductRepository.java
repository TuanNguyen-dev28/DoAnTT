package ecommerce.project.repository;

import ecommerce.project.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE (p.name LIKE '%Men%' OR p.name LIKE '%Shirt%' OR p.name LIKE '%Jeans%' OR p.name LIKE '%Pants%' OR p.name LIKE '%Polo%') AND p.imageUrl IS NOT NULL AND p.imageUrl <> ''")
    List<Product> findMenProducts();

    @Query("SELECT p FROM Product p WHERE (p.name LIKE '%onepiece%' OR p.name LIKE '%sweater%' OR p.name LIKE '%florish%' OR p.name LIKE '%Baggy%' OR p.name LIKE '%Dress%' OR p.name LIKE '%Jeans%' OR p.name LIKE '%Blouse%' OR p.name LIKE '%Skirt%' OR p.name LIKE '%Cardigan%' OR p.name LIKE '%Jumpsuit%') AND p.imageUrl IS NOT NULL AND p.imageUrl <> ''")
    List<Product> findWomenProducts();

    @Query("SELECT p FROM Product p WHERE p.season = :season AND p.imageUrl IS NOT NULL AND p.imageUrl <> ''")
    List<Product> findBySeason(String season);
}
