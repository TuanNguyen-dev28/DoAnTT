package ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ecommerce.project.repository.ProductRepository;

@Controller
public class homeController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("newArrivals", productRepository.findByIsNewArrivalTrue());
        return "index";
    }

    @GetMapping("/shop/accessories")
    public String accessories(Model model) {
        model.addAttribute("products", productRepository.findByCategory("accessories"));
        model.addAttribute("title", "Accessories");
        return "accessories";
    }

    @GetMapping("/shop/clothes")
    public String clothes(Model model) {
        model.addAttribute("products", productRepository.findByCategory("clothes"));
        model.addAttribute("title", "Clothes");
        return "clothes";
    }

    @GetMapping("/shop/best-sellers")
    public String bestSellers(Model model) {
        model.addAttribute("products", productRepository.findByIsBestSellerTrue());
        model.addAttribute("title", "Best Sellers");
        return "best-sellers";
    }

    @GetMapping("/shop/limited-edition")
    public String limitedEdition(Model model) {
        model.addAttribute("products", productRepository.findByIsLimitedEditionTrue());
        model.addAttribute("title", "Limited Edition");
        return "limited-edition";
    }

    @GetMapping("/shop/new-arrivals")
    public String newArrivals(Model model) {
        model.addAttribute("products", productRepository.findByIsNewArrivalTrue());
        model.addAttribute("title", "New Arrivals");
        return "new-arrivals";
    }
}
