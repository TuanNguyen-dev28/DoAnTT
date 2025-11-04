package ecommerce.project.service;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.CartItem;
import ecommerce.project.entity.Order;
import ecommerce.project.entity.OrderItem;
import ecommerce.project.entity.Product;
import ecommerce.project.repository.CartRepository;
import ecommerce.project.repository.CartItemRepository;
import ecommerce.project.repository.OrderRepository;
import ecommerce.project.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public Order createOrderFromCart(Cart cart, String fullName, String email, String phone, String shippingAddress) {
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // Create new order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setFullName(fullName);
        order.setEmail(email);
        order.setPhone(phone);
        order.setShippingAddress(shippingAddress);
        order.setTotalPrice(cart.getTotalPrice());

        // Convert cart items to order items
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            
            // Update product stock
            Product product = cartItem.getProduct();
            int newStock = product.getStockQuantity() - cartItem.getQuantity();
            if (newStock < 0) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }
            product.setStockQuantity(newStock);
            
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        
        // Save order
        Order savedOrder = orderRepository.save(order);
        
        // Clear cart after order is created - delete all cart items from database
        Set<CartItem> cartItemsToDelete = new HashSet<>(cart.getCartItems());
        for (CartItem cartItem : cartItemsToDelete) {
            cartItemRepository.delete(cartItem);
        }
        cart.getCartItems().clear();
        cart.setTotalItems(0);
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = findById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}

