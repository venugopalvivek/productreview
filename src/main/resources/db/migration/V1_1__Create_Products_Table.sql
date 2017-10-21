CREATE TABLE products (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    total_score DOUBLE(2,1) DEFAULT 0.0,
    reviews_count INT DEFAULT 0,

    PRIMARY KEY (id)
);