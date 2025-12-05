package ecommerce.project.service;

import ecommerce.project.entity.Wishlist;

import java.util.List;

public interface WishlistService {
    void addToWishlist(Long productId, String username);
    void removeFromWishlist(Long productId, String username);
    List<Wishlist> getUserWishlist(String username);
    boolean isInWishlist(Long productId, String username);
    int getWishlistCount(String username);
}

