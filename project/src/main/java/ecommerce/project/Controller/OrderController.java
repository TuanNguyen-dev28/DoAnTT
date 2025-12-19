package ecommerce.project.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ecommerce.project.entity.Order;
import ecommerce.project.entity.User;
import ecommerce.project.repository.OrderRepository;
import ecommerce.project.repository.UserRepository;
import ecommerce.project.service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/{id}")
    public String viewOrder(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Check if user owns this order or is admin
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        
        if (!isAdmin && !order.getUser().getId().equals(user.getId())) {
            return "redirect:/orders";
        }

        model.addAttribute("order", order);
        return "order-detail";
    }

    @GetMapping("/orders")
    public String orderHistory(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("orders", orderRepository.findByUserOrderByOrderDateDesc(user));
        return "order-history";
    }

    @PostMapping("/order/{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long id, 
                              @RequestParam("reason") String reason,
                              Principal principal, 
                              RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            orderService.cancelOrder(id, principal.getName(), reason);
            redirectAttributes.addFlashAttribute("success", "Order cancelled successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/order/" + id;
    }
}
