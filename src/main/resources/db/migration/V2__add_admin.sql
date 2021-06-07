INSERT INTO users (id, archive, name, password, role, email, bucket_id)
VALUES (1, false, 'admin', '$2a$10$6vU7AsXGozUiU95WsEUlQOE8dSjhrbSuuIXdUSRuYBTP95unc3Wzi', 'ADMIN', 'mail@mail.ru', null);

ALTER SEQUENCE user_seq RESTART WITH 2;