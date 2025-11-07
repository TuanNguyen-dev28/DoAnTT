package ecommerce.project.repository;

import ecommerce.project.entity.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findAllByOrderByCreatedAtDesc();
    
    Page<BlogPost> findAllByOrderByCreatedAtDesc(Pageable pageable);
    
    List<BlogPost> findTop3ByOrderByCreatedAtDesc();
    
    List<BlogPost> findByCategoryOrderByCreatedAtDesc(String category);
    
    Page<BlogPost> findByCategoryOrderByCreatedAtDesc(String category, Pageable pageable);
    
    @Query("SELECT DISTINCT b.category FROM BlogPost b WHERE b.category IS NOT NULL")
    List<String> findAllDistinctCategories();
}

