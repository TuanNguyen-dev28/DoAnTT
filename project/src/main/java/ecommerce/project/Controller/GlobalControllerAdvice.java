package ecommerce.project.controller;

import ecommerce.project.entity.ContactInfo;
import ecommerce.project.entity.User;
import ecommerce.project.repository.ContactInfoRepository;
import ecommerce.project.repository.UserRepository;
import ecommerce.project.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private WishlistService wishlistService;

    @ModelAttribute
    public void addGlobalAttributes(Model model, Principal principal) {
        // Initialize totalItems to 0 by default
        int totalItems = 0;
        int totalWishlistItems = 0;
        
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName()).orElse(null);
            if (user != null && user.getCart() != null) {
                totalItems = user.getCart().getTotalItems();
            }
            totalWishlistItems = wishlistService.getWishlistCount(principal.getName());
        }
        
        // Always add totalItems to model (even if 0)
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalWishlistItems", totalWishlistItems);
        
        // Add contact info to all pages
        ContactInfo contactInfo = contactInfoRepository.findFirstByOrderByIdAsc().orElse(null);
        model.addAttribute("contactInfo", contactInfo);
    }
}