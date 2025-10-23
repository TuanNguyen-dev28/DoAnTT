package ecommerce.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Cho phép truy cập các tài nguyên tĩnh và trang chủ, trang đăng nhập
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/vendor.css", "/style.css").permitAll()
                        .requestMatchers("/login", "/register").permitAll()
                        // Yêu cầu xác thực cho tất cả các request khác
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Sử dụng trang đăng nhập tùy chỉnh
                        .loginPage("/login")
                        // Endpoint xử lý đăng nhập (phải khớp với action trong form)
                        .loginProcessingUrl("/login")
                        // Chuyển hướng sau khi đăng nhập thành công
                        .defaultSuccessUrl("/", true)
                        // Cho phép tất cả mọi người truy cập trang đăng nhập
                        .permitAll()
                )
                .logout(logout -> logout
                        // URL để thực hiện logout
                        .logoutUrl("/logout")
                        // Chuyển hướng sau khi logout thành công
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}
