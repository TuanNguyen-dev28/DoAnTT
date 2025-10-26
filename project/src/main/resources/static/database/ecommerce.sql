

-- 2. Sử dụng cơ sở dữ liệu vừa tạo
USE ecommerce;
SELECT id, name, category FROM product ORDER BY category, id;

-- 3. Tạo bảng 'product' để lưu trữ thông tin sản phẩm
-- Cấu trúc của bảng này khớp với file model/Product.java của bạn.


-- 4. Thêm một vài dữ liệu mẫu vào bảng
INSERT INTO product (name, price, image_url, category, is_best_seller, is_limited_edition, is_new_arrival) VALUES
('Dark Florish Onepiece', 95.00, 'images/clothes-1.jpg', 'clothes', 1, 0, 1),
('Baggy Shirt', 55.00, 'images/clothes-2.jpg', 'clothes', 0, 1, 0),
('Cotton Off-white Shirt', 65.00, 'images/clothes-3.jpg', 'clothes', 1, 0, 0),
('Classic Denim Jacket', 120.00, 'images/clothes-4.jpg', 'clothes', 0, 1, 0),
('Casual Hoodie', 70.00, 'images/clothes-5.jpg', 'clothes', 0, 0, 0),
('Slim Fit Jeans', 85.00, 'images/clothes-6.jpg', 'clothes', 0, 0, 0),
('Pleated Midi Skirt', 60.00, 'images/clothes-7.jpg', 'clothes', 0, 1, 0),
('Summer Linen Pants', 75.00, 'images/clothes-8.jpg', 'clothes', 0, 0, 1),
('Vintage Leather Blazer', 110.00, 'images/clothes-9.jpg', 'clothes', 0, 0, 1),
('High-Waisted Cargo Pants', 80.00, 'images/clothes-10.jpg', 'clothes', 0, 1, 0),
('Oversized Sweater Knit', 65.00, 'images/clothes-11.jpg', 'clothes', 0, 0, 0),
('Silk Wrap Dress', 130.00, 'images/clothes-12.jpg', 'clothes', 0, 0, 1),
('Silver Hoop Earrings', 25.00, 'images/accessory-1.jpg', 'accessories', 1, 0, 0),
('Leather Handbag', 80.00, 'images/accessory-2.jpg', 'accessories', 0, 1, 0),
('Minimalist Watch', 120.00, 'images/accessory-3.jpg', 'accessories', 1, 0, 1),
('Classic Sunglasses', 45.00, 'images/accessory-4.jpg', 'accessories', 0, 0, 0),
('Wool Scarf', 30.00, 'images/accessory-5.jpg', 'accessories', 0, 0, 1),
('Beanie Hat', 20.00, 'images/accessory-6.jpg', 'accessories', 0, 0, 0),
('Elegant Necklace', 60.00, 'images/accessory-7.jpg', 'accessories', 0, 0, 1),
('Canvas Tote Bag', 35.00, 'images/accessory-8.jpg', 'accessories', 0, 1, 0),
('Pearl Drop Earrings', 40.00, 'images/accessory-9.jpg', 'accessories', 0, 0, 0),
('Vintage Leather Belt', 50.00, 'images/accessory-10.jpg', 'accessories', 0, 0, 0),
('Silk Hair Scrunchie', 15.00, 'images/accessory-11.jpg', 'accessories', 0, 0, 1),
('Classic Wallet', 65.00, 'images/accessory-12.jpg', 'accessories', 0, 0, 1);


-- 5. (Tùy chọn) Kiểm tra xem dữ liệu đã được thêm thành công chưa
SELECT * FROM product;
