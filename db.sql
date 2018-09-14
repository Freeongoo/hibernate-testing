CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL UNIQUE,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_roles` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `role_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  FOREIGN KEY (`user_id`) REFERENCES users (`id`) on delete cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users` (`user_name`, `first_name`, `last_name`, `password`) VALUES
('admin', 'John', 'Down', MD5('manager'));
INSERT INTO `users` (`user_name`, `first_name`, `last_name`, `password`) VALUES
('user', 'Mike', 'Ostin', MD5('user'));
INSERT INTO `users` (`user_name`, `first_name`, `last_name`, `password`) VALUES
('manager', 'Hilary', 'Clinton', MD5('manager'));

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
('1', '2');
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
('2', '0');
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
('3', '1');

-- 2 - admin
-- 1 - manager
-- 0 - user