--liquibase formatted sql

--changeset yevheniy:1
CREATE TABLE database.goods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);

--changeset yevheniy:2
CREATE TABLE database.orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    order_time TIMESTAMP NOT NULL,
    CONSTRAINT status_check CHECK (status IN ('CREATED', 'PAID'))
);

--changeset yevheniy:3
CREATE TABLE database.order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT ,
    goods_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES database.orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_goods FOREIGN KEY (goods_id) REFERENCES database.goods (id) ON DELETE CASCADE
);

--changeset yevheniy:4
INSERT INTO database.goods (name, price, quantity) VALUES
('iPhone 16', 999.99, 100),
('Samsung Galaxy S24', 799.99, 100),
('Google Pixel', 599.99, 100),
('OnePlus 10 Pro', 699.99, 100),
('Sony Xperia', 899.99, 100);

--changeset yevheniy:5
INSERT INTO database.orders (status, order_time) VALUES
('CREATED', CURRENT_TIMESTAMP),
('CREATED', CURRENT_TIMESTAMP),
('CREATED', CURRENT_TIMESTAMP),
('CREATED', CURRENT_TIMESTAMP),
('PAID', CURRENT_TIMESTAMP);

--changeset yevheniy:6
INSERT INTO database.order_items (order_id, goods_id, quantity) VALUES
(1, 1, 2),
(1, 2, 3),
(2, 3, 1),
(2, 4, 1),
(3, 4, 5),
(4, 5, 5),
(5, 5, 10);



