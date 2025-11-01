
CREATE DATABASE ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. Sử dụng cơ sở dữ liệu vừa tạo
USE ecommerce;

-- 3. Tạo bảng 'product' để lưu trữ thông tin sản phẩm
-- Cấu trúc của bảng này khớp với file model/Product.java của bạn.
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    image_url VARCHAR(255),
    season VARCHAR(50)
);

-- 4. Thêm một vài dữ liệu mẫu vào bảng
INSERT INTO product (name, price, image_url, season) VALUES
('Dark florish onepiece', 95.00, 'images/product-item-1.jpg', 'Summer'),
('Baggy Shirt', 55.00, 'images/product-item-2.jpg', 'Summer'),
('Cotton off-white shirt', 65.00, 'images/product-item-3.jpg', 'Spring'),
('Crop sweater', 50.00, 'images/product-item-4.jpg', 'Autumn'),
('Classic Men T-Shirt', 45.00, 'images/product-item-5.jpg', 'Summer'),
('Slim Fit Jeans', 85.00, 'images/product-item-6.jpg', 'Autumn'),
('Men Bomber Jacket', 120.00, 'images/product-item-7.jpg', 'Winter'),
('Formal Dress Shirt', 75.00, 'images/product-item-8.jpg', 'Spring'),
('Men Chino Pants', 70.00, 'images/product-item-9.jpg', 'Spring'),
('Polo Shirt', 60.00, 'images/product-item-10.jpg', 'Summer'),
('Leather Belt', 35.00, 'images/product-item-11.jpg', 'Autumn'),
('Spring Light Jacket', 85.00, 'images/product-item-18.jpg', 'Spring'),
('Elegant Floral Dress', 99.00, 'images/product-item-19.jpg', 'Spring'),
('Graphic Print T-Shirt', 30.00, 'images/product-item-20.jpg', 'Summer'),
('Denim Shorts', 45.00, 'images/product-item-21.jpg', 'Summer'),
('Plaid Scarf', 25.00, 'images/product-item-23.jpg', 'Autumn'),
('Heavy Winter Parka', 180.00, 'images/product-item-24.jpg', 'Winter'),
('Woolen Beanie', 22.00, 'images/product-item-25.jpg', 'Winter');

-- Xóa tất cả các sản phẩm không có hình ảnh
DELETE FROM product WHERE image_url IS NULL OR image_url = '';

-- 5. (Tùy chọn) Kiểm tra xem dữ liệu đã được thêm thành công chưa
SELECT * FROM product;
