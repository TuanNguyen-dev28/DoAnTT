
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

import java.math.BigDecimal;
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
    public Cart themSanPhamVaoGio(Product sanPham, int soLuong, String tenNguoiDung) {
        User nguoiDung = userRepository.findByUsername(tenNguoiDung).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        Cart gioHang = cartRepository.findByUser_Id(nguoiDung.getId());

        if (gioHang == null) {
            gioHang = new Cart();
            gioHang.setUser(nguoiDung);
        }

        Set<CartItem> cacMucGioHang = gioHang.getCartItems();
        CartItem mucGioHang = timMucGioHang(cacMucGioHang, sanPham.getId());

        if (cacMucGioHang == null) {
            cacMucGioHang = new HashSet<>();
            gioHang.setCartItems(cacMucGioHang);
        }

        mucGioHang = timMucGioHang(cacMucGioHang, sanPham.getId());

        if (mucGioHang == null) {
            mucGioHang = new CartItem();
            mucGioHang.setProduct(sanPham);
            mucGioHang.setCart(gioHang);
            mucGioHang.setQuantity(soLuong);
            mucGioHang.setTotalPrice(sanPham.getPrice().multiply(BigDecimal.valueOf(soLuong)));
            cacMucGioHang.add(mucGioHang);
        } else {
            mucGioHang.setQuantity(mucGioHang.getQuantity() + soLuong);
            BigDecimal newTotalPrice = mucGioHang.getTotalPrice().add(sanPham.getPrice().multiply(BigDecimal.valueOf(soLuong)));
            mucGioHang.setTotalPrice(newTotalPrice);
        }
        gioHang.setCartItems(cacMucGioHang);

        return updateCartTotals(gioHang);
    }

    @Override
    public Cart capNhatGioHang(Product sanPham, int soLuong, String tenNguoiDung) {
        User nguoiDung = userRepository.findByUsername(tenNguoiDung).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        Cart gioHang = cartRepository.findByUser_Id(nguoiDung.getId());

        Set<CartItem> cacMucGioHang = gioHang.getCartItems();
        CartItem muc = timMucGioHang(cacMucGioHang, sanPham.getId());

        if (muc != null) {
            muc.setQuantity(soLuong);
            muc.setTotalPrice(sanPham.getPrice().multiply(BigDecimal.valueOf(soLuong)));
            itemRepository.save(muc);

            updateCartTotals(gioHang);
        }
        return gioHang;
    }

    @Override
    public void xoaMucKhoiGioHang(Long id, String tenNguoiDung) {
        User nguoiDung = userRepository.findByUsername(tenNguoiDung).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        Cart gioHang = cartRepository.findByUser_Id(nguoiDung.getId());

        Set<CartItem> cacMucGioHang = gioHang.getCartItems();
        CartItem muc = cacMucGioHang.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);

        if (muc != null) {
            cacMucGioHang.remove(muc);
            itemRepository.delete(muc);
            updateCartTotals(gioHang);
        }
    }

    private CartItem timMucGioHang(Set<CartItem> cacMucGioHang, Long sanPhamId) {
        if (cacMucGioHang == null) {
            return null;
        }
        return cacMucGioHang.stream()
                .filter(muc -> muc.getProduct().getId().equals(sanPhamId))
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