package ecommerce.project.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class RoleBasedRedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationSuccessHandler userSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/");
    private final AuthenticationSuccessHandler adminSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/admin/dashboard");

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("ROLE_ADMIN")) {
                // Nếu là admin, chuyển hướng đến trang admin dashboard
                this.adminSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }
        }

        // Nếu không phải admin (mặc định là user), chuyển hướng về trang chủ
        this.userSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }
}
