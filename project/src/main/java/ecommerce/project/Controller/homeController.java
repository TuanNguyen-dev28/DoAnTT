package ecommerce.project.Controller;

import ecommerce.project.model.Product;
import ecommerce.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class homeController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home(Model model) {
        // Lấy tất cả sản phẩm từ database
        List<Product> newArrivals = productRepository.findAll();

        // Để demo, nếu database trống, chúng ta sẽ thêm một vài sản phẩm mẫu
        if (newArrivals.isEmpty()) {
            productRepository.save(new Product("Dark florish onepiece", 95.00, "images/product-item-1.jpg"));
            productRepository.save(new Product("Baggy Shirt", 55.00, "images/product-item-2.jpg"));
            productRepository.save(new Product("Cotton off-white shirt", 65.00, "images/product-item-3.jpg"));
            productRepository.save(new Product("Crop sweater", 50.00, "images/product-item-4.jpg"));
            newArrivals = productRepository.findAll();
        }

        // Thêm danh sách sản phẩm vào model để Thymeleaf có thể truy cập
        model.addAttribute("newArrivals", newArrivals);

        return "index"; // Trả về file index.html trong thư mục templates
    }

}
