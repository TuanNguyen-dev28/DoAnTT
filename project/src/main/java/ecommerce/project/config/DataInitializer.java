package ecommerce.project.config;

import ecommerce.project.entity.Role;
import ecommerce.project.entity.User;
import ecommerce.project.repository.RoleRepository;
import ecommerce.project.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository,
                                          RoleRepository roleRepository,
                                          PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Tạo các vai trò nếu chúng chưa tồn tại
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role("ROLE_ADMIN"));
            }
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role("ROLE_USER"));
            }

            // 2. Tạo tài khoản admin nếu chưa có
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("28122005"));
                admin.setEmail("admin@example.com");
                Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
            }
        };
    }
}
