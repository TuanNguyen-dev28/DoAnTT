-- File này sẽ được Spring Boot tự động chạy khi khởi động
-- nhờ các cấu hình trong application.properties.
-- Nó chỉ chứa các lệnh INSERT để thêm dữ liệu mẫu.
-- Các lệnh CREATE TABLE đã được Hibernate tự động xử lý dựa trên các Entity.

-- 1. Thêm dữ liệu mẫu vào bảng product
INSERT INTO product (name, price, image_url) VALUES
('Dark florish onepiece', 95.00, 'images/product-item-1.jpg'),
('Baggy Shirt', 55.00, 'images/product-item-2.jpg'),
('Cotton off-white shirt', 65.00, 'images/product-item-3.jpg'),
('Crop sweater', 50.00, 'images/product-item-4.jpg');
