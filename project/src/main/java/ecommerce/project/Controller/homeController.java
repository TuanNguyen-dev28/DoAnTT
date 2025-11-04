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
import ecommerce.project.repository.ContactInfoRepository;
import ecommerce.project.repository.ProductRepository;
import java.util.List;

@Controller
public class homeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AboutRepository aboutRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @GetMapping("/")
    public String home(Model model) {
        // Lấy danh sách sản phẩm và giới hạn số lượng để hiển thị trên trang chủ
        model.addAttribute("newArrivals", productRepository.findByIsNewArrivalTrue().stream().limit(8).toList());
        model.addAttribute("bestSellers", productRepository.findByIsBestSellerTrue().stream().limit(8).toList());
        // Lấy một vài sản phẩm bất kỳ cho mục "You may also like"
        model.addAttribute("youMayLike", productRepository.findAll().stream().limit(8).toList());
        
        // Load contact info for footer
        ContactInfo contactInfo = contactInfoRepository.findFirstByOrderByIdAsc().orElse(null);
        model.addAttribute("contactInfo", contactInfo);
        
        return "index";
    }

    @GetMapping("/shop/{type}")
    public String shopPage(@PathVariable("type") String type, Model model) {
        List<Product> products;
        String title;
        String viewName; // Tên view sẽ được trả về
        switch (type) {
            case "accessories" -> { products = productRepository.findByCategory("accessories"); title = "Accessories"; viewName = "accessories"; }
            case "clothes" -> { products = productRepository.findByCategory("clothes"); title = "Clothes"; viewName = "clothes"; }
            case "best-sellers" -> { products = productRepository.findByIsBestSellerTrue(); title = "Best Sellers"; viewName = "best-sellers"; }
            case "limited-edition" -> { products = productRepository.findByIsLimitedEditionTrue(); title = "Limited Edition"; viewName = "limited-edition"; }
            case "new-arrivals" -> { products = productRepository.findByIsNewArrivalTrue(); title = "New Arrivals"; viewName = "new-arrivals"; }
            case "shop-for-woman" -> { products = productRepository.findWomenProducts(); title = "Shop For Woman"; viewName = "shop_for_woman"; }
            case "shop-for-men" -> { products = productRepository.findMenProducts(); title = "Shop For Men"; viewName = "shop_for_men"; }
            default -> { return "redirect:/"; /* Hoặc trả về trang 404 nếu muốn */ }
        }
        model.addAttribute("products", products);
        model.addAttribute("title", title);
        return viewName;
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
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
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        model.addAttribute("products", products);
        model.addAttribute("title", "Search Results: " + keyword);
        model.addAttribute("keyword", keyword);
        return "search-results";
    }
}
