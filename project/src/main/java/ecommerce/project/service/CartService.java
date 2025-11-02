package ecommerce.project.service;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.Product;
import ecommerce.project.entity.User;

public interface CartService {
    Cart themSanPhamVaoGio(Product sanPham, int soLuong, String tenNguoiDung);

    Cart capNhatGioHang(Product sanPham, int soLuong, String tenNguoiDung);

    void xoaMucKhoiGioHang(Long id, String tenNguoiDung);
}
