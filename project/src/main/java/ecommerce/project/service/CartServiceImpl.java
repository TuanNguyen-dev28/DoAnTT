
package ecommerce.project.service;
import ecommerce.project.entity.Cart;
import ecommerce.project.entity.CartItem;
import ecommerce.project.entity.Product;
import ecommerce.project.entity.User;
import ecommerce.project.repository.CartItemRepository;
import ecommerce.project.repository.CartRepository;
import ecommerce.project.repository.UserRepository;
import ecommerce.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart addItemToCart(Product product, int quantity, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUser_Id(user.getId());

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        Set<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = findCartItem(cartItems, product.getId());

        if (cartItems == null) {
            cartItems = new HashSet<>();
        }

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(quantity * product.getPrice());
            cartItems.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * product.getPrice()));
        }
        cart.setCartItems(cartItems);

        cart.setTotalItems(cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum());
        cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getTotalPrice).sum());

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(Product product, int quantity, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUser_Id(user.getId());

        Set<CartItem> cartItems = cart.getCartItems();
        CartItem item = findCartItem(cartItems, product.getId());

        if (item != null) {
            item.setQuantity(quantity);
            item.setTotalPrice(quantity * product.getPrice());
            itemRepository.save(item);

            cart.setTotalItems(cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum());
            cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getTotalPrice).sum());
        }

        return cartRepository.save(cart);
    }

    @Override
    public void deleteItemFromCart(Long id, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUser_Id(user.getId());

        Set<CartItem> cartItems = cart.getCartItems();
        CartItem item = cartItems.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);

        if (item != null) {
            cartItems.remove(item);
            itemRepository.delete(item);
            cart.setTotalItems(cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum());
            cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getTotalPrice).sum());
            cartRepository.save(cart);
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
}