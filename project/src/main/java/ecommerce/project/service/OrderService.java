package ecommerce.project.service;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.Order;

public interface OrderService {
    Order createOrderFromCart(Cart cart, String fullName, String email, String phone, String shippingAddress);
    Order findById(Long id);
    Order updateOrderStatus(Long orderId, Order.OrderStatus status);
    void cancelOrder(Long orderId, String username, String reason);
}
