DROP SEQUENCE IF EXISTS user_seq;
CREATE SEQUENCE user_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id        int8    not null,
    archive   boolean not null,
    name      varchar(255),
    password  varchar(255),
    role      varchar(255),
    email     varchar(255),
    bucket_id int8,
    primary key (id)
);


DROP SEQUENCE IF EXISTS bucket_seq;
CREATE SEQUENCE bucket_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS buckets CASCADE;
CREATE TABLE buckets
(
    id      int8 not null,
    user_id int8,
    primary key (id)
);

ALTER TABLE IF EXISTS buckets
    ADD CONSTRAINT buckets_fk_user
        FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS users
    ADD CONSTRAINT users_fk_bucket
        FOREIGN KEY (bucket_id) REFERENCES buckets;


DROP SEQUENCE IF EXISTS category_seq;
CREATE SEQUENCE category_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS categories CASCADE;
CREATE TABLE categories
(
    id    int8 not null,
    title varchar(255),
    primary key (id)
);


DROP SEQUENCE IF EXISTS product_seq;
CREATE SEQUENCE product_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products
(
    id    int8 not null,
    price numeric(19, 2),
    title varchar(255),
    primary key (id)
);


DROP TABLE IF EXISTS products_categories CASCADE;
CREATE TABLE products_categories
(
    product_id  int8 not null,
    category_id int8 not null
);

ALTER TABLE IF EXISTS products_categories
    ADD CONSTRAINT products_categories_fk_category
        FOREIGN KEY (category_id) REFERENCES categories;

ALTER TABLE IF EXISTS products_categories
    ADD CONSTRAINT products_categories_fk_products
        FOREIGN KEY (product_id) REFERENCES products;


DROP TABLE IF EXISTS bucket_products CASCADE;
CREATE TABLE bucket_products
(
    bucket_id int8 not null,
    product_id  int8 not null
);

ALTER TABLE IF EXISTS bucket_products
    ADD CONSTRAINT bucket_products_fk_products
        FOREIGN KEY (product_id) REFERENCES products;

ALTER TABLE IF EXISTS bucket_products
    ADD CONSTRAINT bucket_products_fk_bucket
        FOREIGN KEY (bucket_id) REFERENCES buckets;


DROP SEQUENCE IF EXISTS orders_seq;
CREATE SEQUENCE orders_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE orders
(
    id    int8 not null,
    address varchar(255),
    created timestamp,
    updated timestamp,
    status varchar(255),
    sum numeric(19, 2),
    user_id int8,
    primary key (id)
);

ALTER TABLE IF EXISTS orders
    ADD CONSTRAINT orders_fk_user
        FOREIGN KEY (user_id) REFERENCES users;


DROP SEQUENCE IF EXISTS order_details_seq;
CREATE SEQUENCE order_details_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS orders_details CASCADE;
CREATE TABLE orders_details
(
    id    int8 not null,
    amount numeric(19,2),
    price numeric(19,2),
    order_id int8,
    product_id int8,
    details_id int8 not null,
    primary key (id)
);

ALTER TABLE IF EXISTS orders_details
    ADD CONSTRAINT orders_details_fk_order
        FOREIGN KEY (order_id) REFERENCES orders;

ALTER TABLE IF EXISTS orders_details
    ADD CONSTRAINT orders_details_fk_products
        FOREIGN KEY (product_id) REFERENCES products;