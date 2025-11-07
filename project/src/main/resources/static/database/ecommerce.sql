CREATE DATABASE ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ecommerce;


CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stock_quantity` int NOT NULL DEFAULT '0',
  `rating` decimal(3,2) DEFAULT NULL,
  `is_new_arrival` bit(1) NOT NULL DEFAULT b'0',
  `is_best_seller` bit(1) NOT NULL DEFAULT b'0',
  `is_limited_edition` bit(1) NOT NULL DEFAULT b'0',
  `material` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `color` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `available_sizes` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `season` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--
-- Xóa dữ liệu cũ để tránh trùng lặp
TRUNCATE TABLE `product`;

-- Thêm dữ liệu mới với đầy đủ các cột
-- WOMEN PRODUCTS (onepiece, sweater, florish, Baggy, Dress, Skirt, Cardigan, Jumpsuit)
INSERT INTO `product` (`name`, `price`, `image_url`, `description`, `category`, `stock_quantity`, `rating`, `is_new_arrival`, `is_best_seller`, `is_limited_edition`, `material`, `color`, `available_sizes`, `season`) VALUES
('Dark Florish Onepiece', 95.00, 'images/clothes-1.jpg', 'A beautiful dark florish onepiece, perfect for any occasion. Made with high-quality fabric for comfort and style.', 'clothes', 50, 4.50, b'1', b'1', b'0', 'Polyester', 'Black', 'S,M,L', 'Summer'),
('Baggy Shirt', 55.00, 'images/clothes-2.jpg', 'Comfortable and stylish baggy shirt, ideal for a relaxed look. Made from soft cotton.', 'clothes', 75, 4.20, b'0', b'1', b'0', 'Cotton', 'White', 'M,L,XL', 'Spring'),
('Crop Sweater', 50.00, 'images/clothes-4.jpg', 'A trendy crop sweater for a modern look.', 'clothes', 120, 4.00, b'1', b'0', b'0', 'Wool', 'Gray', 'S,M', 'Autumn'),
('Handmade Crop Sweater', 70.00, 'images/clothes-6.jpg', 'A unique handmade crop sweater.', 'clothes', 40, 4.60, b'1', b'0', b'1', 'Wool', 'Beige', 'S,M,L', 'Winter'),
('Pleated Midi Skirt', 60.00, 'images/clothes-7.jpg', 'Elegant pleated midi skirt, perfect for a sophisticated and feminine style. Flowy and comfortable.', 'clothes', 35, 4.10, b'0', b'1', b'0', 'Chiffon', 'Navy', 'S,M,L', 'Spring'),
('Silk Wrap Dress', 130.00, 'images/clothes-12.jpg', 'Luxurious silk wrap dress, elegant and flattering for any figure. Perfect for special occasions.', 'clothes', 25, 4.90, b'0', b'0', b'1', 'Silk', 'Emerald Green', 'S,M,L', 'All'),
('Oversized Sweater Knit', 65.00, 'images/clothes-11.jpg', 'Cozy oversized knit sweater, perfect for chilly days. Soft and comfortable.', 'clothes', 70, 4.50, b'0', b'0', b'0', 'Wool Blend', 'Cream', 'One Size', 'Winter');

-- MEN PRODUCTS (Men, Shirt, Jeans, Pants, Polo)
INSERT INTO `product` (`name`, `price`, `image_url`, `description`, `category`, `stock_quantity`, `rating`, `is_new_arrival`, `is_best_seller`, `is_limited_edition`, `material`, `color`, `available_sizes`, `season`) VALUES
('Men Cotton Shirt', 65.00, 'images/clothes-3.jpg', 'Classic off-white cotton shirt, versatile for both casual and semi-formal wear.', 'clothes', 60, 4.70, b'0', b'1', b'0', 'Cotton', 'Off-white', 'S,M,L,XL', 'All'),
('Classic Denim Jacket', 120.00, 'images/clothes-4.jpg', 'Timeless classic denim jacket, a must-have for every wardrobe. Durable and stylish.', 'clothes', 40, 4.60, b'0', b'1', b'0', 'Denim', 'Blue', 'S,M,L', 'All'),
('Slim Fit Jeans', 85.00, 'images/clothes-6.jpg', 'Modern slim fit jeans, offering a sleek look and comfortable stretch. Ideal for daily wear.', 'clothes', 90, 4.40, b'0', b'0', b'0', 'Denim', 'Dark Blue', '28,30,32,34', 'All'),
('Men Summer Linen Pants', 75.00, 'images/clothes-8.jpg', 'Lightweight summer linen pants, breathable and stylish for warm weather. Relaxed fit.', 'clothes', 65, 4.00, b'1', b'0', b'0', 'Linen', 'Beige', 'S,M,L,XL', 'Summer'),
('Men Vintage Leather Blazer', 110.00, 'images/clothes-9.jpg', 'Stylish vintage leather blazer, adding a touch of class to any outfit. High-quality faux leather.', 'clothes', 30, 4.80, b'1', b'0', b'0', 'Faux Leather', 'Brown', 'S,M,L', 'Autumn'),
('Men High-Waisted Cargo Pants', 80.00, 'images/clothes-10.jpg', 'Trendy high-waisted cargo pants, combining utility and fashion. Multiple pockets for convenience.', 'clothes', 55, 4.20, b'0', b'1', b'0', 'Cotton Blend', 'Olive Green', 'XS,S,M,L', 'All'),
('Men Casual Hoodie', 70.00, 'images/clothes-5.jpg', 'Soft and cozy casual hoodie, perfect for everyday comfort. Features a front pocket.', 'clothes', 80, 4.30, b'0', b'0', b'0', 'Fleece', 'Gray', 'M,L,XL', 'Winter');

-- ACCESSORIES (unisex)
INSERT INTO `product` (`name`, `price`, `image_url`, `description`, `category`, `stock_quantity`, `rating`, `is_new_arrival`, `is_best_seller`, `is_limited_edition`, `material`, `color`, `available_sizes`, `season`) VALUES
('Leather Handbag', 80.00, 'images/accessory-2.jpg', 'Stylish leather handbag, spacious and durable for daily use. Features multiple compartments.', 'accessories', 45, 4.70, b'0', b'1', b'0', 'Genuine Leather', 'Brown', 'One Size', 'All'),
('Silver Hoop Earrings', 25.00, 'images/accessory-1.jpg', 'Elegant silver hoop earrings, a timeless accessory for any look. Hypoallergenic material.', 'accessories', 120, 4.60, b'1', b'0', b'0', 'Sterling Silver', 'Silver', 'One Size', 'All'),
('Minimalist Watch', 120.00, 'images/accessory-3.jpg', 'Sleek minimalist watch, perfect for a sophisticated touch. Water-resistant with a leather strap.', 'accessories', 30, 4.80, b'1', b'1', b'0', 'Stainless Steel, Leather', 'Black', 'One Size', 'All'),
('Classic Sunglasses', 45.00, 'images/accessory-4.jpg', 'UV protected classic sunglasses, offering both style and eye protection. Lightweight frame.', 'accessories', 100, 4.30, b'0', b'0', b'0', 'Plastic, Metal', 'Black', 'One Size', 'Summer'),
('Wool Scarf', 30.00, 'images/accessory-5.jpg', 'Warm and soft wool scarf, ideal for colder seasons. Versatile for various styles.', 'accessories', 70, 4.40, b'1', b'0', b'0', 'Wool', 'Gray', 'One Size', 'Winter'),
('Beanie Hat', 20.00, 'images/accessory-6.jpg', 'Comfortable and stylish beanie hat, perfect for adding a casual touch to your outfit.', 'accessories', 90, 4.10, b'0', b'0', b'0', 'Acrylic', 'Navy', 'One Size', 'Winter'),
('Elegant Necklace', 60.00, 'images/accessory-7.jpg', 'Delicate and elegant necklace, perfect for enhancing any attire. Features a unique pendant.', 'accessories', 50, 4.70, b'1', b'0', b'0', 'Gold Plated Brass', 'Gold', 'One Size', 'All'),
('Canvas Tote Bag', 35.00, 'images/accessory-8.jpg', 'Spacious and eco-friendly canvas tote bag, great for shopping or daily essentials. Durable handles.', 'accessories', 80, 4.00, b'0', b'1', b'0', 'Canvas', 'Natural', 'One Size', 'All'),
('Pearl Drop Earrings', 40.00, 'images/accessory-9.jpg', 'Classic pearl drop earrings, adding a touch of sophistication. Perfect for formal events.', 'accessories', 60, 4.50, b'0', b'0', b'0', 'Sterling Silver, Faux Pearl', 'White', 'One Size', 'All'),
('Vintage Leather Belt', 50.00, 'images/accessory-10.jpg', 'High-quality vintage leather belt, a versatile accessory for jeans or dresses. Adjustable buckle.', 'accessories', 70, 4.20, b'0', b'0', b'0', 'Genuine Leather', 'Dark Brown', 'S,M,L', 'All'),
('Silk Hair Scrunchie', 15.00, 'images/accessory-11.jpg', 'Gentle silk hair scrunchie, prevents hair breakage and adds a chic touch. Various colors available.', 'accessories', 150, 4.60, b'1', b'0', b'0', 'Silk', 'Pink', 'One Size', 'All'),
('Classic Wallet', 65.00, 'images/accessory-12.jpg', 'Compact and functional classic wallet, with multiple card slots and a coin pocket. RFID protected.', 'accessories', 55, 4.30, b'1', b'0', b'0', 'PU Leather', 'Black', 'One Size', 'All');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` bigint NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `email`, `enabled`, `password`, `username`) VALUES
(1, 'admin@example.com', b'1', '$2a$10$wINWOMxZ8pWnth7pEx9cEutGjdSkgl0vkEIfhl7gwFj/G/LlIZZay', 'admin'),
(2, 'nat281205@gmail.com', b'1', '$2a$10$rIuRWkEqSBragwx/qBP9I.unpvD150zwCwGdaJzJMHxWEnL/vHg3i', 'bao');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `carts`
--

CREATE TABLE `carts` (
  `id` bigint NOT NULL,
  `total_price` double NOT NULL,
  `total_items` int NOT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart_items`
--

CREATE TABLE `cart_items` (
  `id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `total_price` double NOT NULL,
  `cart_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_9emv3bel0c3600a4s5h2idshn` (`user_id`);

--
-- Chỉ mục cho bảng `cart_items`
--
ALTER TABLE `cart_items`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`);

--
-- Chỉ mục cho bảng `users_roles`
--
ALTER TABLE `users_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT cho bảng `carts`
--
ALTER TABLE `carts`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT cho bảng `cart_items`
--
ALTER TABLE `cart_items`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `cart_items`
--
ALTER TABLE `cart_items`
  ADD CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKpcttvq8xufnws4b9t2ds6v1i` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`);
--
-- Các ràng buộc cho bảng `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `order_date` datetime NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'PENDING',
  `shipping_address` text COLLATE utf8mb4_unicode_ci,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_orders_user` (`user_id`),
  CONSTRAINT `FK_orders_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_items`
--

CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `quantity` int NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_items_order` (`order_id`),
  KEY `FK_order_items_product` (`product_id`),
  CONSTRAINT `FK_order_items_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FK_order_items_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `contacts`
--

CREATE TABLE `contacts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `subject` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `message` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `is_read` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `about`
--

CREATE TABLE `about` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` text COLLATE utf8mb4_unicode_ci,
  `content` text COLLATE utf8mb4_unicode_ci,
  `mission` text COLLATE utf8mb4_unicode_ci,
  `vision` text COLLATE utf8mb4_unicode_ci,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `contact_info`
--

CREATE TABLE `contact_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `password_reset_tokens`
--

CREATE TABLE `password_reset_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `expiry_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_token` (`token`),
  KEY `FK_password_reset_user` (`user_id`),
  CONSTRAINT `FK_password_reset_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `blog_posts`
--

CREATE TABLE `blog_posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text COLLATE utf8mb4_unicode_ci,
  `excerpt` text COLLATE utf8mb4_unicode_ci,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dữ liệu mẫu cho bảng `blog_posts`
--

INSERT INTO `blog_posts` (`title`, `content`, `excerpt`, `image_url`, `category`, `created_at`, `updated_at`) VALUES
('How to look outstanding in pastel', 
'<p>Dignissim lacus, turpis ut suspendisse vel tellus. Turpis purus, gravida orci, fringilla dignissim lacus, turpis ut suspendisse vel tellus. Ac sed eu fringilla odio mi. Consequat pharetra at magna imperdiet cursus ac faucibus sit libero.</p><p>Ultricies quam nunc, lorem sit lorem urna, pretium aliquam ut. In vel, quis donec dolor id in. Pulvinar commodo mollis diam sed facilisis at cursus imperdiet cursus ac faucibus sit faucibus sit libero.</p><p>Fashion is not just about wearing clothes, it\'s about expressing yourself. Pastel colors have become a trend that never goes out of style. They bring a soft, elegant touch to any outfit and make you stand out in a crowd.</p>', 
'Dignissim lacus, turpis ut suspendisse vel tellus. Turpis purus, gravida orci, fringilla...', 
'images/post-image1.jpg', 
'Fashion', 
NOW(), 
NOW()),

('Top 10 fashion trend for summer', 
'<p>Summer fashion is all about staying cool while looking hot. This season brings fresh trends that will make you the center of attention.</p><h3>1. Lightweight Fabrics</h3><p>Choose breathable materials like cotton, linen, and chiffon to stay comfortable in the heat.</p><h3>2. Bright Colors</h3><p>Embrace vibrant hues that reflect the energy of summer. Think coral, turquoise, and sunny yellow.</p><h3>3. Flowy Dresses</h3><p>Maxi and midi dresses in flowing fabrics are perfect for summer days and nights.</p><p>Remember, fashion is personal. Wear what makes you feel confident and beautiful!</p>', 
'Turpis purus, gravida orci, fringilla dignissim lacus, turpis ut suspendisse vel tellus...', 
'images/post-image2.jpg', 
'Fashion', 
NOW(), 
NOW()),

('Crazy fashion with unique moment', 
'<p>Fashion is an art form, and every outfit tells a story. Creating unique moments through your style choices is what makes fashion exciting.</p><p>Don\'t be afraid to experiment with bold combinations, mix patterns, or try unexpected color pairings. The most memorable fashion moments come from taking risks.</p><p>Whether you\'re attending a special event or just going about your day, let your outfit reflect your personality and mood. Fashion should be fun, expressive, and uniquely yours.</p>', 
'Turpis purus, gravida orci, fringilla dignissim lacus, turpis ut suspendisse vel tellus...', 
'images/post-image3.jpg', 
'Fashion', 
NOW(), 
NOW());

COMMIT;
