

-- 2. Sử dụng cơ sở dữ liệu vừa tạo
USE ecommerce;

-- 3. Tạo bảng 'product' để lưu trữ thông tin sản phẩm
-- Cấu trúc của bảng này khớp với file model/Product.java của bạn.


-- 4. Thêm một vài dữ liệu mẫu vào bảng
INSERT INTO product (name, price, image_url, category, is_best_seller, is_limited_edition, is_new_arrival) VALUES
('Dark florish onepiece', 95.00, 'images/product-item-1.jpg', 'clothes', 0, 0, 0),
('Baggy Shirt', 55.00, 'images/product-item-2.jpg', 'clothes', 0, 0, 0),
('Cotton off-white shirt', 65.00, 'images/product-item-3.jpg', 'clothes', 0, 0, 0),
('Crop sweater', 50.00, 'images/product-item-4.jpg', 'clothes', 0, 0, 0),

('Silver Hoop Earrings', 25.00, 'images/accessory-1.jpg', 'accessories', 0, 0, 0),
('Leather Handbag', 80.00, 'images/accessory-2.jpg', 'accessories', 0, 0, 0),
('Minimalist Watch', 120.00, 'images/accessory-3.jpg', 'accessories', 0, 0, 0),
('Classic Sunglasses', 45.00, 'images/accessory-4.jpg', 'accessories', 0, 0, 0),
('Wool Scarf', 30.00, 'images/accessory-5.jpg', 'accessories', 0, 0, 0),
('Beanie Hat', 20.00, 'images/accessory-6.jpg', 'accessories', 0, 0, 0),
('Elegant Necklace', 60.00, 'images/accessory-7.jpg', 'accessories', 0, 0, 0),
('Canvas Tote Bag', 35.00, 'images/accessory-8.jpg', 'accessories', 0, 0, 0);

-- 5. (Tùy chọn) Kiểm tra xem dữ liệu đã được thêm thành công chưa
SELECT * FROM product;

