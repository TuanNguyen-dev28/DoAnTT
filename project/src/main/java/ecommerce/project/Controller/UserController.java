package ecommerce.project.controller;

import ecommerce.project.entity.Role;
import ecommerce.project.entity.User;
import ecommerce.project.repository.RoleRepository;
import ecommerce.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Optional;

@Controller
public class UserController { // Đổi tên class

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

        Optional<Role> userRoleOpt = roleRepository.findByName("ROLE_USER");
        if (userRoleOpt.isEmpty()) {
            // Xử lý trường hợp không tìm thấy role, ví dụ: báo lỗi
            model.addAttribute("error", "Default role 'ROLE_USER' not found in database.");
            return "register";
        }
        user.setRoles(Collections.singleton(userRoleOpt.get()));

        userRepository.save(user);
        return "redirect:/login?register_success";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
