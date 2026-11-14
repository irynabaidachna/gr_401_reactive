-- Створення ролей
INSERT INTO roles (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

-- Створення тестових користувачів
INSERT INTO users (username, password)
VALUES
    ('1111', '$2y$10$jFa27DNoGmzP3UQBHWvWDOh6bSSdsPn1M1Po53Ot0OiGEywryoU8a'),
    ('2222', '$2y$10$elwgkgvugJfmcehLKx2aQ.0SjDYL.YYK55yVMj.zAKtaiBBl9Ksce');

-- Призначення ролей першому користувачу
INSERT INTO roles_has_users (user_id, role_id)
SELECT u.id, r.id
FROM users u
         CROSS JOIN roles r
WHERE u.username = '1111'
  AND r.name IN ('ROLE_USER', 'ROLE_ADMIN');

-- Призначення ролі другому користувачу
INSERT INTO roles_has_users (user_id, role_id)
SELECT u.id, r.id
FROM users u
         CROSS JOIN roles r
WHERE u.username = '2222'
  AND r.name = 'ROLE_USER';
