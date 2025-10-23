package ecommerce.project.controller;

import ecommerce.project.entity.Role;
import ecommerce.project.entity.User;
import ecommerce.project.repository.RoleRepository;
import ecommerce.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class UserController { // Đổi tên class

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
        return "redirect:/login?register_success";
    }
}
