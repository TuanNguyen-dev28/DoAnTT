package ecommerce.project.controller;

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
        // Thêm danh sách sản phẩm vào model để Thymeleaf có thể truy cập
        model.addAttribute("newArrivals", newArrivals);

        return "index"; // Trả về file index.html trong thư mục templates
    }

    @GetMapping("/shop/shop-for-woman")
    public String shopForWoman(Model model) {
        // Lấy các sản phẩm dành cho nữ từ database
        List<Product> womenProducts = productRepository.findWomenProducts();
        model.addAttribute("products", womenProducts);
        return "shop_for_woman"; // Trả về file shop_for_woman.html
    }

    @GetMapping("/shop/shop-for-men")
    public String shopForMen(Model model) {
        // Lấy các sản phẩm dành cho nam từ database
        List<Product> menProducts = productRepository.findMenProducts();
        model.addAttribute("products", menProducts);
        return "shop_for_men"; // Trả về file shop_for_men.html
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("springProducts", productRepository.findBySeason("Spring"));
        model.addAttribute("summerProducts", productRepository.findBySeason("Summer"));
        model.addAttribute("autumnProducts", productRepository.findBySeason("Autumn"));
        model.addAttribute("winterProducts", productRepository.findBySeason("Winter"));
        return "blog"; // Trả về file blog.html
    }

}
