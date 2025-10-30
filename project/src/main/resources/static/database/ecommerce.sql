CREATE DATABASE ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ecommerce;


CREATE TABLE `product` (
  `id` bigint NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` double NOT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `name`, `price`, `image_url`) VALUES
(1, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(2, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(3, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(4, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(5, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(6, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(7, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(8, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(9, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(10, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(11, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(12, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(13, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(14, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(15, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(16, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(17, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(18, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(19, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(20, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(21, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(22, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(23, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(24, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(25, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(26, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(27, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(28, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(29, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(30, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(31, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(32, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(33, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(34, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(35, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(36, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(37, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(38, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(39, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(40, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(41, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(42, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(43, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(44, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(45, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(46, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(47, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(48, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(49, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(50, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(51, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(52, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(53, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(54, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(55, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(56, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(57, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(58, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(59, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(60, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(61, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(62, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(63, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(64, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(65, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(66, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(67, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(68, 'Crop sweater', 50, 'images/product-item-4.jpg'),
(69, 'Dark florish onepiece', 95, 'images/product-item-1.jpg'),
(70, 'Baggy Shirt', 55, 'images/product-item-2.jpg'),
(71, 'Cotton off-white shirt', 65, 'images/product-item-3.jpg'),
(72, 'Crop sweater', 50, 'images/product-item-4.jpg');

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
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `carts`
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
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

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
-- Cấu trúc bảng cho bảng `carts`
CREATE TABLE `carts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `total_price` double NOT NULL,
  `total_items` int NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9emv3bel0c3600a4s5h2idshn` (`user_id`),
  CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Cấu trúc bảng cho bảng `cart_items`
CREATE TABLE `cart_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `total_price` double NOT NULL,
  `cart_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKpcttvq8xufnws4b9t2ds6v1i` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
