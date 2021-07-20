INSERT INTO users (id, archive, email, name, password, role, bucket_id)
values (1, false, 'mail@mail.ru', 'admin', '$2a$10$P3s1b7mz.t71PhMK2Ci4SujnuoflkqwfDjhPSU5NePH.1RuAlYH7G', 'ADMIN', null);

ALTER SEQUENCE user_seq RESTART WITH 2;