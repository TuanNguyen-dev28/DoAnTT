
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
('Crop sweater', 50.00, 'images/product-item-4.jpg');

-- 5. (Tùy chọn) Kiểm tra xem dữ liệu đã được thêm thành công chưa
SELECT * FROM product;
