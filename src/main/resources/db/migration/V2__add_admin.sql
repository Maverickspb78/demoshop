INSERT INTO users (id, archive, name, password, role, email, bucket_id)
VALUES (1, false, 'admin', 'pass', 'ADMIN', 'mail@mail.ru', null);

ALTER SEQUENCE user_seq RESTART WITH 2;