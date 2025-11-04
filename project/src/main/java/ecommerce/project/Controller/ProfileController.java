package ecommerce.project.controller;

import ecommerce.project.entity.User;
import ecommerce.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if email is already taken by another user
        if (!user.getEmail().equals(email)) {
            if (userRepository.findByEmail(email).isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Email already exists!");
                return "redirect:/profile";
            }
        }

        user.setEmail(email);
        
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/profile";
    }
}


