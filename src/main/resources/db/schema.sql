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


