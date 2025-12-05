package ecommerce.project.service;

import ecommerce.project.entity.Product;
import ecommerce.project.entity.User;
import ecommerce.project.entity.Wishlist;
import ecommerce.project.repository.ProductRepository;
import ecommerce.project.repository.UserRepository;
import ecommerce.project.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public void addToWishlist(Long productId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Kiểm tra xem sản phẩm đã có trong wishlist chưa
        if (!wishlistRepository.existsByUserAndProduct(user, product)) {
            Wishlist wishlist = new Wishlist(user, product);
            wishlistRepository.save(wishlist);
        }
    }

    @Override
    @Transactional
    public void removeFromWishlist(Long productId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        wishlistRepository.deleteByUserAndProduct(user, product);
    }

    @Override
    public List<Wishlist> getUserWishlist(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return wishlistRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public boolean isInWishlist(Long productId, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return false;
        }
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return false;
        }
        return wishlistRepository.existsByUserAndProduct(user, product);
    }

    @Override
    public int getWishlistCount(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return 0;
        }
        return wishlistRepository.countByUser(user);
    }
}

