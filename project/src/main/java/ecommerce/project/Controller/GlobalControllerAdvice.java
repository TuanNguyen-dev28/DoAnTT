package ecommerce.project.controller;

import ecommerce.project.entity.ContactInfo;
import ecommerce.project.entity.User;
import ecommerce.project.repository.ContactInfoRepository;
import ecommerce.project.repository.UserRepository;
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

    @ModelAttribute
    public void addGlobalAttributes(Model model, Principal principal) {
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName()).orElse(null);
            if (user != null && user.getCart() != null) {
                model.addAttribute("totalItems", user.getCart().getTotalItems());
            }
        }
        
        // Add contact info to all pages
        ContactInfo contactInfo = contactInfoRepository.findFirstByOrderByIdAsc().orElse(null);
        model.addAttribute("contactInfo", contactInfo);
    }
}