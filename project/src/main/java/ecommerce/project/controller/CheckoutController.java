package ecommerce.project.controller;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.Order;
import ecommerce.project.entity.User;
import ecommerce.project.repository.CartRepository;
import ecommerce.project.repository.UserRepository;
import ecommerce.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/checkout")
    public String checkoutPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartRepository.findByUser_Id(user.getId());
        
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("shippingAddress") String shippingAddress,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartRepository.findByUser_Id(user.getId());
        
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty!");
            return "redirect:/cart";
        }

        try {
            Order order = orderService.createOrderFromCart(cart, fullName, email, phone, shippingAddress);
            redirectAttributes.addFlashAttribute("success", "Order placed successfully! Order ID: " + order.getId());
            return "redirect:/order/" + order.getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/checkout";
        }
    }
}

