package ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ecommerce.project.entity.About;
import ecommerce.project.entity.ContactInfo;
import ecommerce.project.entity.Product;
import ecommerce.project.repository.AboutRepository;
import ecommerce.project.repository.BlogPostRepository;
import ecommerce.project.repository.ContactInfoRepository;
import ecommerce.project.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Controller
public class homeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AboutRepository aboutRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @GetMapping("/")
    public String home(Model model) {
        // Lấy danh sách sản phẩm và giới hạn số lượng để hiển thị trên trang chủ
        model.addAttribute("newArrivals", productRepository.findByIsNewArrivalTrue().stream().limit(8).toList());
        model.addAttribute("bestSellers", productRepository.findByIsBestSellerTrue().stream().limit(8).toList());
        // Lấy một vài sản phẩm bất kỳ cho mục "You may also like"
        model.addAttribute("youMayLike", productRepository.findAll().stream().limit(8).toList());
        
        // Load blog posts for "Read Blog Posts" section (latest posts - no limit)
        model.addAttribute("blogPosts", blogPostRepository.findAllByOrderByCreatedAtDesc());
        
        // Load contact info for footer
        ContactInfo contactInfo = contactInfoRepository.findFirstByOrderByIdAsc().orElse(null);
        model.addAttribute("contactInfo", contactInfo);
        
        return "index";
    }

    @GetMapping("/shop/{type}")
    public String shopPage(
            @PathVariable("type") String type,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {
        Page<Product> productPage;
        String title;
        String viewName; // Tên view sẽ được trả về
        
        // Pagination: 3 products per page
        Pageable pageable = PageRequest.of(page, 3);
        
        switch (type) {
            case "accessories" -> { 
                productPage = productRepository.findByCategory("accessories", pageable); 
                title = "Accessories"; 
                viewName = "accessories"; 
            }
            case "clothes" -> { 
                productPage = productRepository.findByCategory("clothes", pageable); 
                title = "Clothes"; 
                viewName = "clothes"; 
            }
            case "best-sellers" -> { 
                productPage = productRepository.findByIsBestSellerTrue(pageable); 
                title = "Best Sellers"; 
                viewName = "best-sellers"; 
            }
            case "limited-edition" -> { 
                productPage = productRepository.findByIsLimitedEditionTrue(pageable); 
                title = "Limited Edition"; 
                viewName = "limited-edition"; 
            }
            case "new-arrivals" -> { 
                productPage = productRepository.findByIsNewArrivalTrue(pageable); 
                title = "New Arrivals"; 
                viewName = "new-arrivals"; 
            }
            case "shop-for-woman" -> { 
                productPage = productRepository.findWomenProducts(pageable); 
                title = "Shop For Woman"; 
                viewName = "shop_for_woman"; 
            }
            case "shop-for-men" -> { 
                productPage = productRepository.findMenProducts(pageable); 
                title = "Shop For Men"; 
                viewName = "shop_for_men"; 
            }
            default -> { return "redirect:/"; /* Hoặc trả về trang 404 nếu muốn */ }
        }
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("title", title);
        model.addAttribute("shopType", type);
        return viewName;
    }

    @GetMapping("/about")
    public String about(Model model) {
        About about = aboutRepository.findFirstByOrderByIdAsc().orElse(null);
        model.addAttribute("about", about);
        return "about";
    }

    // --- BẮT ĐẦU: THÊM PHƯƠNG THỨC XEM CHI TIẾT SẢN PHẨM ---
    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model) {
        // Tìm sản phẩm theo ID, nếu không thấy sẽ báo lỗi
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        
        // Lấy 4 sản phẩm liên quan (cùng danh mục) để hiển thị
        List<Product> relatedProducts = productRepository.findTop4ByCategoryAndIdNot(product.getCategory(), id);
        model.addAttribute("relatedProducts", relatedProducts);

        return "single-product"; // Trả về trang single-product.html
    }
    // --- KẾT THÚC ---

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {
        // Pagination: 3 products per page
        Pageable pageable = PageRequest.of(page, 3);
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable);
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("title", "Search Results: " + keyword);
        model.addAttribute("keyword", keyword);
        return "search-results";
    }
}
