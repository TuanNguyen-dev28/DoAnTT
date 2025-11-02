package ecommerce.project.controller;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.Product;
import ecommerce.project.entity.User;
import ecommerce.project.repository.ProductRepository;
import ecommerce.project.repository.UserRepository;
import ecommerce.project.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();

        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            model.addAttribute("check", true);
        } else {
            model.addAttribute("check", false);
            model.addAttribute("cart", cart);
        }
        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            Principal principal,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
        String username = principal.getName();
        cartService.themSanPhamVaoGio(product, quantity, username);
        redirectAttributes.addFlashAttribute("success", "Added to cart successfully!");
        
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }

    @PostMapping("/update-cart")
    public String capNhatGioHang(@RequestParam("productId") Long productId,
                             @RequestParam("quantity") int quantity,
                             Principal principal) {
        Product product = productRepository.findById(productId).orElseThrow();
        String username = principal.getName();
        cartService.capNhatGioHang(product, quantity, username);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/delete-from-cart/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteItem(@PathVariable("id") Long id, Principal principal) {
        cartService.xoaMucKhoiGioHang(id, principal.getName());
        return "redirect:/cart";
    }
}
