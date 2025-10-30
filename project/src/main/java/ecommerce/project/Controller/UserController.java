package ecommerce.project.controller;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.Role;
import ecommerce.project.entity.User;
import ecommerce.project.repository.RoleRepository;
import ecommerce.project.repository.CartRepository;
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
public class UserController { 

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, CartRepository cartRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
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
        // Thêm vai trò vào Set đã được khởi tạo trong Entity User,
        // thay vì thay thế nó bằng một Set mới.
        user.getRoles().add(userRoleOpt.get());

        userRepository.save(user);

        // Tạo giỏ hàng mới cho người dùng
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        return "redirect:/login?register_success";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
