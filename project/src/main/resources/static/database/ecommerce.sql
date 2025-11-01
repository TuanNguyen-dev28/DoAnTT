
CREATE DATABASE ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. Sử dụng cơ sở dữ liệu vừa tạo
USE ecommerce;

-- 3. Tạo bảng 'product' để lưu trữ thông tin sản phẩm
-- Cấu trúc của bảng này khớp với file model/Product.java của bạn.
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    image_url VARCHAR(255)
);

-- 4. Thêm một vài dữ liệu mẫu vào bảng
INSERT INTO product (name, price, image_url) VALUES
('Dark florish onepiece', 95.00, 'images/product-item-1.jpg'),
('Baggy Shirt', 55.00, 'images/product-item-2.jpg'),
('Cotton off-white shirt', 65.00, 'images/product-item-3.jpg'),
('Crop sweater', 50.00, 'images/product-item-4.jpg'),
('Classic Men T-Shirt', 45.00, 'images/product-item-5.jpg'),
('Slim Fit Jeans', 85.00, 'images/product-item-6.jpg'),
('Men Bomber Jacket', 120.00, 'images/product-item-7.jpg'),
('Formal Dress Shirt', 75.00, 'images/product-item-8.jpg'),
('Men Chino Pants', 70.00, 'images/product-item-9.jpg'),
('Polo Shirt', 60.00, 'images/product-item-10.jpg'),
('Leather Belt', 35.00, 'images/product-item-11.jpg');

-- 5. (Tùy chọn) Kiểm tra xem dữ liệu đã được thêm thành công chưa
SELECT * FROM product;
