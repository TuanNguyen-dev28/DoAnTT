package ecommerce.project.controller;

import ecommerce.project.entity.PasswordResetToken;
import ecommerce.project.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(
            @RequestParam("email") String email,
            RedirectAttributes redirectAttributes) {
        
        PasswordResetToken token = passwordResetService.createPasswordResetToken(email);
        
        // Always show success message to prevent email enumeration
        redirectAttributes.addFlashAttribute("success", 
            "If an account with that email exists, we have sent a password reset link.");
        
        // In production, send email here
        // For now, we'll log the token (in production, send email with link)
        if (token != null) {
            System.out.println("Password reset token for " + email + ": " + token.getToken());
            System.out.println("Reset link: http://localhost:8080/reset-password?token=" + token.getToken());
        }
        
        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(
            @RequestParam(value = "token", required = false) String token,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        if (token == null || token.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invalid or missing reset token.");
            return "redirect:/forgot-password";
        }

        // Validate token
        if (passwordResetService.validateToken(token) == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired reset token.");
            return "redirect:/forgot-password";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {
        
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            redirectAttributes.addFlashAttribute("token", token);
            return "redirect:/reset-password?token=" + token;
        }

        if (password.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "Password must be at least 6 characters long.");
            redirectAttributes.addFlashAttribute("token", token);
            return "redirect:/reset-password?token=" + token;
        }

        boolean success = passwordResetService.resetPassword(token, password);
        
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Your password has been reset successfully. Please login with your new password.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired reset token.");
            return "redirect:/forgot-password";
        }
    }
}

