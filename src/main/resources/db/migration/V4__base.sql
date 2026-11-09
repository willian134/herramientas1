CREATE TABLE IF NOT EXISTS product (
                                       id SERIAL PRIMARY KEY,
                                       nombre VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DOUBLE PRECISION,
    stock INT NOT NULL DEFAULT 0
    );

CREATE TABLE IF NOT EXISTS orders (
                                      id SERIAL PRIMARY KEY,
                                      customer_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    verification_code VARCHAR(10)
    );

CREATE TABLE IF NOT EXISTS order_products (
                                              order_id INT REFERENCES orders(id),
    product_id INT REFERENCES product(id)
    );