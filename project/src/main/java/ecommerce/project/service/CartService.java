package ecommerce.project.service;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.Product;

public interface CartService {
    Cart addToCart(Product product, int quantity, String username);

    Cart updateCart(Product product, int quantity, String username);

    void removeFromCart(Long id, String username);
}
