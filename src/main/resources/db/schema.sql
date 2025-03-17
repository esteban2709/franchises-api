CREATE TABLE IF NOT EXISTS franchises (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS branches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    franchise_id BIGINT NOT NULL,
    FOREIGN KEY (franchise_id) REFERENCES franchises(id)
);

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS branch_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    stock INT NOT NULL,
    FOREIGN KEY (branch_id) REFERENCES branches(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- INSERT INTO franchises (id, name) VALUES (1, 'Franquicia 1');
--
-- INSERT INTO branches (id , name, franchise_id) VALUES
-- (1,'Sucursal 1', 1),
-- (2, 'Sucursal 2', 1),
-- (3, 'Sucursal 3', 1);

-- INSERT INTO products (id, name) VALUES (1, 'Producto 1');
-- INSERT INTO products (id, name) VALUES (2, 'Producto 2');
-- INSERT INTO products (id, name) VALUES (3, 'Producto 3');
-- INSERT INTO products (id, name) VALUES (4, 'Producto 4');
--
-- INSERT INTO branch_products (branch_id, product_id, stock) VALUES
-- (1, 1, 50),
-- (1, 2, 60),
-- (1, 3, 70),
-- (1, 4, 80),
-- (2, 1, 90),
-- (2, 2, 10),
-- (2, 3, 110),
-- (2, 4, 20),
-- (3, 1, 130),
-- (3, 2, 14),
-- (3, 3, 50),
-- (3, 4, 16);