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

-- 6. Tạo bảng 'users' để lưu thông tin người dùng
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- 7. Tạo bảng 'roles' để lưu các vai trò
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- 8. Tạo bảng join 'users_roles' để quản lý mối quan hệ nhiều-nhiều
CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- 9. Thêm các vai trò mặc định
INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

-- 10. Thêm người dùng 'admin' và 'user' với mật khẩu đã được mã hóa
-- Mật khẩu gốc là '28122005'. Chuỗi hash dưới đây được tạo bằng BCryptPasswordEncoder.
-- Mật khẩu gốc của 'user' là 'password'.
INSERT INTO users (username, password, email, enabled) VALUES
('admin', '$2a$10$E.qgXfFTvVCLuY2bL9VvJO2iN0c7uVSCQxGWzmcj2/a/kZ5c.wLqG', 'admin@example.com', TRUE),
('user', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'user@example.com', TRUE);

-- 11. Gán vai trò cho người dùng
INSERT INTO users_roles (user_id, role_id) VALUES
((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
((SELECT id FROM users WHERE username = 'user'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));