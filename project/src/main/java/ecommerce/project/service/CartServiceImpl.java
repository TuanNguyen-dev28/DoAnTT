
package ecommerce.project.service;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.CartItem;
import ecommerce.project.entity.Product;
import ecommerce.project.entity.User;
import ecommerce.project.repository.CartItemRepository;
import ecommerce.project.repository.CartRepository;
import ecommerce.project.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Cart addToCart(Product product, int quantity, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUser_Id(user.getId());

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        Set<CartItem> cartItems = cart.getCartItems();

        if (cartItems == null) {
            cartItems = new HashSet<>();
            cart.setCartItems(cartItems);
        }

        CartItem cartItem = findCartItem(cartItems, product.getId());

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cartItems.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            BigDecimal newTotalPrice = cartItem.getTotalPrice().add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cartItem.setTotalPrice(newTotalPrice);
        }
        cart.setCartItems(cartItems);

        return updateCartTotals(cart);
    }

    @Override
    @Transactional
    public Cart updateCart(Product product, int quantity, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUser_Id(user.getId());

        Set<CartItem> cartItems = cart.getCartItems();
        CartItem item = findCartItem(cartItems, product.getId());

        if (item != null) {
            item.setQuantity(quantity);
            item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            itemRepository.save(item);

            updateCartTotals(cart);
        }
        return cart;
    }

    @Override
    @Transactional
    public void removeFromCart(Long id, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUser_Id(user.getId());

        Set<CartItem> cartItems = cart.getCartItems();
        CartItem item = cartItems.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);

        if (item != null) {
            cartItems.remove(item);
            itemRepository.delete(item);
            updateCartTotals(cart);
        }
    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long productId) {
        if (cartItems == null) {
            return null;
        }
        return cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    private Cart updateCartTotals(Cart cart) {
        int totalItems = cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);
        return cartRepository.save(cart);
    }
}