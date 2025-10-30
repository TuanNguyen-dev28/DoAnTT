package ecommerce.project.service;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.Product;
import ecommerce.project.entity.User;

public interface CartService {
    Cart addItemToCart(Product product, int quantity, String username);

    Cart updateCart(Product product, int quantity, String username);

    void deleteItemFromCart(Long id, String username);
}

