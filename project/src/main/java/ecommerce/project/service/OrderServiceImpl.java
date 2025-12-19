package ecommerce.project.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.CartItem;
import ecommerce.project.entity.Order;
import ecommerce.project.entity.OrderItem;
import ecommerce.project.entity.Product;
import ecommerce.project.repository.CartItemRepository;
import ecommerce.project.repository.CartRepository;
import ecommerce.project.repository.OrderRepository;
import ecommerce.project.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

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
        Long cartId = cart.getId();
        
        if (cartId == null) {
            // If cart doesn't have an ID, it might not be persisted yet
            // Just clear the in-memory cart items
            cart.getCartItems().clear();
            cart.setTotalItems(0);
            cart.setTotalPrice(BigDecimal.ZERO);
            return savedOrder;
        }
        
        // Delete all cart items for this cart using a single query (more efficient)
        cartItemRepository.deleteAllByCartId(cartId);
        
        // Update cart totals directly using JPQL (avoids detached entity issues)
        cartRepository.clearCartTotals(cartId, BigDecimal.ZERO);
        
        // Also clear in-memory cart for consistency
        cart.getCartItems().clear();
        cart.setTotalItems(0);
        cart.setTotalPrice(BigDecimal.ZERO);

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

    @Override
    @Transactional
    public void cancelOrder(Long orderId, String username, String reason) {
        Order order = findById(orderId);

        if (!order.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("You are not authorized to cancel this order");
        }

        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new IllegalStateException("Order cannot be cancelled because it is " + order.getStatus());
        }

        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
        }

        order.setCancellationReason(reason);
        log.info("Order {} cancelled by user {}. Reason: {}", orderId, username, reason);

        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
