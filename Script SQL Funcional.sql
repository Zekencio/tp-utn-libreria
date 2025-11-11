CREATE DATABASE book_store;
USE book_store;
-- DROP DATABASE book_store;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE authors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_date DATE
);

CREATE TABLE sellers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    seller_user_id INT UNIQUE,
    CONSTRAINT fk_seller_user FOREIGN KEY (seller_user_id) REFERENCES users(id)
);

CREATE TABLE genres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE cards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(255) NOT NULL,
    bank VARCHAR(255) NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    owner_id INT,
    CONSTRAINT fk_card_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE sales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    user_id INT,
    card_id INT,
    CONSTRAINT fk_sale_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_sale_card FOREIGN KEY (card_id) REFERENCES cards(id)
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DOUBLE NOT NULL,
    stock INT NOT NULL,
    author_id INT,
    seller_id INT,
    cart_user_id INT,
    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES authors(id),
    CONSTRAINT fk_book_seller FOREIGN KEY (seller_id) REFERENCES sellers(id),
    CONSTRAINT fk_cart_user FOREIGN KEY (cart_user_id) REFERENCES users(id)
);

CREATE TABLE books_genres (
    book_id INT,
    genre_id INT,
    PRIMARY KEY (book_id, genre_id),
    CONSTRAINT fk_bg_book FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT fk_bg_genre FOREIGN KEY (genre_id) REFERENCES genres(id)
);

CREATE TABLE sales_books (
    book_id INT,
    sale_id INT,
    PRIMARY KEY (book_id, sale_id),
    CONSTRAINT fk_sb_book FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT fk_sb_sale FOREIGN KEY (sale_id) REFERENCES sales(id)
);


-- Create un admin user directly in db with login: admin@bookstore.com, contraseña: admin2025

START TRANSACTION;

INSERT INTO user (name, password)
VALUES ('admin@bookstore.com', '$2b$12$1L/HVmqEdQxJMeF4ddi8d.Xo/CWoEfzA1P6GGaZ8j62cBtQ0P2lNW');

SET @new_user_id = LAST_INSERT_ID();
INSERT INTO user_roles (user_id, role) VALUES
  (@new_user_id, 'ROLE_ADMIN');

COMMIT;