CREATE TABLE IF NOT EXISTS `user_info` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`user_id` varchar(255) NOT NULL,
`email` varchar(255) NOT NULL,
`created_at` datetime DEFAULT NULL,
`updated_at` datetime DEFAULT NULL,
PRIMARY KEY (`id`));

CREATE INDEX `user_idx` ON `user_info`(`user_id`);
CREATE INDEX `email_x` ON `user_info`(`email`);

-- Create table for saving otp
CREATE TABLE IF NOT EXISTS `otp_info` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `user_contact` varchar(255) NOT NULL,
   `contact_type` varchar(255) NOT NULL,
   `otp_type` varchar(255) NOT NULL,
   `otp` varchar(10) NOT NULL,
   `is_active` TINYINT DEFAULT NULL,
   `created_at` datetime NOT NULL,
   PRIMARY KEY (`id`),
   KEY `active_user_contact_idx` (`user_contact`, `contact_type`, `otp_type`, `is_active`)
 );
