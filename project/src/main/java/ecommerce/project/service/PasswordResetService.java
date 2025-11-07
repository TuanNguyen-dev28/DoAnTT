package ecommerce.project.service;

import ecommerce.project.entity.PasswordResetToken;
import ecommerce.project.entity.User;
import ecommerce.project.repository.PasswordResetTokenRepository;
import ecommerce.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TOKEN_LENGTH = 64;

    /**
     * Generate a secure random token
     */
    public String generateToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return token.toString();
    }

    /**
     * Create a password reset token for a user
     */
    @Transactional
    public PasswordResetToken createPasswordResetToken(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return null; // Don't reveal if email exists or not
        }

        User user = userOpt.get();
        
        // Delete existing token for this user if any
        tokenRepository.findByUser_Id(user.getId()).ifPresent(tokenRepository::delete);
        
        // Create new token
        String token = generateToken();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        return tokenRepository.save(resetToken);
    }

    /**
     * Validate token and return user if valid
     */
    public User validateToken(String token) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return null;
        }

        PasswordResetToken resetToken = tokenOpt.get();
        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            return null;
        }

        return resetToken.getUser();
    }

    /**
     * Reset password for user
     */
    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        User user = validateToken(token);
        if (user == null) {
            return false;
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Delete used token
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);

        return true;
    }

    /**
     * Clean up expired tokens (can be called by a scheduled task)
     */
    @Transactional
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}

