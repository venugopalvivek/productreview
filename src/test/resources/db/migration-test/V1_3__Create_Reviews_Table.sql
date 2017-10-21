CREATE TABLE reviews (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    product_id INT NOT NULL,
    score INT NOT NULL,
    comments VARCHAR(512),
    reviewer_name VARCHAR(128) NOT NULL,
    reviewer_email VARCHAR(256) NOT NULL,
    state INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP NOT NULL,
);