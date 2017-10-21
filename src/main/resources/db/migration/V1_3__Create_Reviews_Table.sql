CREATE TABLE reviews (
    id INT NOT NULL AUTO_INCREMENT,
    product_id INT NOT NULL,
    score INT NOT NULL,
    comments VARCHAR(512),
    reviewer_name VARCHAR(128) NOT NULL,
    reviewer_email VARCHAR(256) NOT NULL,
    state INT NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,

    PRIMARY KEY (id)
);