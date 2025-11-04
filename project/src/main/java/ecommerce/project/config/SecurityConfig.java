package ecommerce.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler roleBasedRedirectAuthenticationSuccessHandler() {
        return new RoleBasedRedirectAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Các trang/hành động yêu cầu xác thực
                        .requestMatchers("/cart", "/add-to-cart", "/update-cart", "/delete-from-cart/**", 
                                        "/checkout", "/orders/**", "/order/**").authenticated()
                        // Yêu cầu vai trò ADMIN cho các trang admin
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Cho phép tất cả các request khác (bao gồm trang chủ, xem sản phẩm, tài nguyên tĩnh, v.v.)
                        // Điều này giúp đảm bảo người dùng có thể xem mọi thứ trừ các mục đã được bảo vệ ở trên.
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        // Sử dụng trang đăng nhập tùy chỉnh
                        .loginPage("/login")
                        // Endpoint xử lý đăng nhập (phải khớp với action trong form)
                        .loginProcessingUrl("/login")
                        // Sử dụng handler tùy chỉnh để chuyển hướng sau khi đăng nhập
                        .successHandler(roleBasedRedirectAuthenticationSuccessHandler())
                        // Cho phép tất cả mọi người truy cập trang đăng nhập
                        .permitAll()
                )
                .logout(logout -> logout
                        // URL để thực hiện logout
                        .logoutUrl("/logout")
                        // Chuyển hướng sau khi logout thành công
                        .logoutSuccessUrl("/?logout_success")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                );
        return http.build();
    }

    // Bean này cần thiết để quản lý các sự kiện của session
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
