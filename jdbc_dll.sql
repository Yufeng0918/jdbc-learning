CREATE TABLE blob_test
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    big_bit BLOB
);
CREATE TABLE clob_test
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    big_text TEXT
);
CREATE TABLE user
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    birthday DATE,
    money DECIMAL(8,2) NOT NULL
)CREATE PROCEDURE addUser(pname VARCHAR, birthday DATE, c FLOAT, pid INT);