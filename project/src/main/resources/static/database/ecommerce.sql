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
INSERT INTO `product` (`id`, `name`, `price`, `image_url`, `description`, `category`, `stock_quantity`, `rating`, `is_new_arrival`, `is_best_seller`, `is_limited_edition`, `material`, `color`, `available_sizes`, `season`) VALUES
(1, 'Dark Florish Onepiece', 95.00, 'images/product-item-1.jpg', 'A beautiful dark florish onepiece, perfect for any occasion.', 'clothes', 50, 4.50, b'1', b'1', b'0', 'Polyester', 'Black', 'S,M,L', 'Summer'),
(2, 'Baggy Shirt', 55.00, 'images/product-item-2.jpg', 'A comfortable and stylish baggy shirt.', 'clothes', 100, 4.20, b'1', b'0', b'0', 'Cotton', 'White', 'M,L,XL', 'Spring'),
(3, 'Cotton Off-white Shirt', 65.00, 'images/product-item-3.jpg', 'A classic off-white shirt made from pure cotton.', 'clothes', 80, 4.80, b'0', b'1', b'0', 'Cotton', 'Off-white', 'S,M,L', 'All'),
(4, 'Crop Sweater', 50.00, 'images/product-item-4.jpg', 'A trendy crop sweater for a modern look.', 'clothes', 120, 4.00, b'1', b'0', b'0', 'Wool', 'Gray', 'S,M', 'Autumn'),
(5, 'Leather Handbag', 120.00, 'images/product-item-5.jpg', 'A stylish leather handbag for everyday use.', 'accessories', 30, 4.90, b'0', b'1', b'1', 'Leather', 'Brown', NULL, 'All'),
(6, 'Handmade Crop Sweater', 70.00, 'images/product-item-6.jpg', 'A unique handmade crop sweater.', 'clothes', 40, 4.60, b'1', b'0', b'1', 'Wool', 'Beige', 'S,M,L', 'Winter'),
(7, 'Silver Necklace', 85.00, 'images/product-item-7.jpg', 'An elegant silver necklace with a unique pendant.', 'accessories', 60, 4.70, b'0', b'0', b'0', 'Silver', 'Silver', NULL, 'All'),
(8, 'Stylish Sunglasses', 45.00, 'images/product-item-8.jpg', 'Protect your eyes with these stylish sunglasses.', 'accessories', 150, 4.30, b'1', b'0', b'0', 'Plastic', 'Black', NULL, 'Summer');

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
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

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
COMMIT;
