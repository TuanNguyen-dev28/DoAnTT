

-- 2. Sử dụng cơ sở dữ liệu vừa tạo
USE ecommerce;
TRUNCATE TABLE product;

-- 3. Tạo bảng 'product' để lưu trữ thông tin sản phẩm
-- Cấu trúc của bảng này khớp với file model/Product.java của bạn và được mở rộng để hiển thị chi tiết sản phẩm.


-- 4. Thêm một vài dữ liệu mẫu vào bảng
INSERT INTO product (name, price, image_url, category, is_best_seller, is_limited_edition, is_new_arrival, description, material, color, available_sizes, stock_quantity, rating) VALUES
('Dark Florish Onepiece', 95.00, 'images/clothes-1.jpg', 'clothes', 1, 0, 1, 'A beautiful dark florish onepiece, perfect for any occasion. Made with high-quality fabric for comfort and style.', 'Polyester', 'Black', 'S,M,L', 50, 4.5),
('Baggy Shirt', 55.00, 'images/clothes-2.jpg', 'clothes', 0, 1, 0, 'Comfortable and stylish baggy shirt, ideal for a relaxed look. Made from soft cotton.', 'Cotton', 'White', 'M,L,XL', 75, 4.2),
('Cotton Off-white Shirt', 65.00, 'images/clothes-3.jpg', 'clothes', 1, 0, 0, 'Classic off-white cotton shirt, versatile for both casual and semi-formal wear.', 'Cotton', 'Off-white', 'S,M,L,XL', 60, 4.7),
('Classic Denim Jacket', 120.00, 'images/clothes-4.jpg', 'clothes', 0, 1, 0, 'Timeless classic denim jacket, a must-have for every wardrobe. Durable and stylish.', 'Denim', 'Blue', 'S,M,L', 40, 4.6),
('Casual Hoodie', 70.00, 'images/clothes-5.jpg', 'clothes', 0, 0, 0, 'Soft and cozy casual hoodie, perfect for everyday comfort. Features a front pocket.', 'Fleece', 'Gray', 'M,L,XL', 80, 4.3),
('Slim Fit Jeans', 85.00, 'images/clothes-6.jpg', 'clothes', 0, 0, 0, 'Modern slim fit jeans, offering a sleek look and comfortable stretch. Ideal for daily wear.', 'Denim', 'Dark Blue', '28,30,32,34', 90, 4.4),
('Pleated Midi Skirt', 60.00, 'images/clothes-7.jpg', 'clothes', 0, 1, 0, 'Elegant pleated midi skirt, perfect for a sophisticated and feminine style. Flowy and comfortable.', 'Chiffon', 'Navy', 'S,M,L', 35, 4.1),
('Summer Linen Pants', 75.00, 'images/clothes-8.jpg', 'clothes', 0, 0, 1, 'Lightweight summer linen pants, breathable and stylish for warm weather. Relaxed fit.', 'Linen', 'Beige', 'S,M,L,XL', 65, 4.0),
('Vintage Leather Blazer', 110.00, 'images/clothes-9.jpg', 'clothes', 0, 0, 1, 'Stylish vintage leather blazer, adding a touch of class to any outfit. High-quality faux leather.', 'Faux Leather', 'Brown', 'S,M,L', 30, 4.8),
('High-Waisted Cargo Pants', 80.00, 'images/clothes-10.jpg', 'clothes', 0, 1, 0, 'Trendy high-waisted cargo pants, combining utility and fashion. Multiple pockets for convenience.', 'Cotton Blend', 'Olive Green', 'XS,S,M,L', 55, 4.2),
('Oversized Sweater Knit', 65.00, 'images/clothes-11.jpg', 'clothes', 0, 0, 0, 'Cozy oversized knit sweater, perfect for chilly days. Soft and comfortable.', 'Wool Blend', 'Cream', 'One Size', 70, 4.5),
('Silk Wrap Dress', 130.00, 'images/clothes-12.jpg', 'clothes', 0, 0, 1, 'Luxurious silk wrap dress, elegant and flattering for any figure. Perfect for special occasions.', 'Silk', 'Emerald Green', 'S,M,L', 25, 4.9),
('Silver Hoop Earrings', 25.00, 'images/accessory-1.jpg', 'accessories', 1, 0, 0, 'Elegant silver hoop earrings, a timeless accessory for any look. Hypoallergenic material.', 'Sterling Silver', 'Silver', 'One Size', 120, 4.6),
('Leather Handbag', 80.00, 'images/accessory-2.jpg', 'accessories', 0, 1, 0, 'Stylish leather handbag, spacious and durable for daily use. Features multiple compartments.', 'Genuine Leather', 'Brown', 'One Size', 45, 4.7),
('Minimalist Watch', 120.00, 'images/accessory-3.jpg', 'accessories', 1, 0, 1, 'Sleek minimalist watch, perfect for a sophisticated touch. Water-resistant with a leather strap.', 'Stainless Steel, Leather', 'Black', 'One Size', 30, 4.8),
('Classic Sunglasses', 45.00, 'images/accessory-4.jpg', 'accessories', 0, 0, 0, 'UV protected classic sunglasses, offering both style and eye protection. Lightweight frame.', 'Plastic, Metal', 'Black', 'One Size', 100, 4.3),
('Wool Scarf', 30.00, 'images/accessory-5.jpg', 'accessories', 0, 0, 1, 'Warm and soft wool scarf, ideal for colder seasons. Versatile for various styles.', 'Wool', 'Gray', 'One Size', 70, 4.4),
('Beanie Hat', 20.00, 'images/accessory-6.jpg', 'accessories', 0, 0, 0, 'Comfortable and stylish beanie hat, perfect for adding a casual touch to your outfit.', 'Acrylic', 'Navy', 'One Size', 90, 4.1),
('Elegant Necklace', 60.00, 'images/accessory-7.jpg', 'accessories', 0, 0, 1, 'Delicate and elegant necklace, perfect for enhancing any attire. Features a unique pendant.', 'Gold Plated Brass', 'Gold', 'One Size', 50, 4.7),
('Canvas Tote Bag', 35.00, 'images/accessory-8.jpg', 'accessories', 0, 1, 0, 'Spacious and eco-friendly canvas tote bag, great for shopping or daily essentials. Durable handles.', 'Canvas', 'Natural', 'One Size', 80, 4.0),
('Pearl Drop Earrings', 40.00, 'images/accessory-9.jpg', 'accessories', 0, 0, 0, 'Classic pearl drop earrings, adding a touch of sophistication. Perfect for formal events.', 'Sterling Silver, Faux Pearl', 'White', 'One Size', 60, 4.5),
('Vintage Leather Belt', 50.00, 'images/accessory-10.jpg', 'accessories', 0, 0, 0, 'High-quality vintage leather belt, a versatile accessory for jeans or dresses. Adjustable buckle.', 'Genuine Leather', 'Dark Brown', 'S,M,L', 70, 4.2),
('Silk Hair Scrunchie', 15.00, 'images/accessory-11.jpg', 'accessories', 0, 0, 1, 'Gentle silk hair scrunchie, prevents hair breakage and adds a chic touch. Various colors available.', 'Silk', 'Pink', 'One Size', 150, 4.6),
('Classic Wallet', 65.00, 'images/accessory-12.jpg', 'accessories', 0, 0, 1, 'Compact and functional classic wallet, with multiple card slots and a coin pocket. RFID protected.', 'PU Leather', 'Black', 'One Size', 55, 4.3);

-- 5. (Tùy chọn) Kiểm tra xem dữ liệu đã được thêm thành công chưa
SELECT * FROM product;
