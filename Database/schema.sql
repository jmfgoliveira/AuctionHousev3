/*
			Network and Computer Security
				1st Semester 2017/18	
		
			Secure online auction website

				João Oliveira 72944
				Gonçalo Louro 78982
			Matilde Nascimento 82083

					Grupo 36

					Schema
*/

DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS Purchases;
DROP TABLE IF EXISTS Auction;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Login;

CREATE TABLE User (
	id						integer(10) NOT NULL AUTO_INCREMENT,
	name					varchar(64) NOT NULL,
	email					varchar(64) NOT NULL UNIQUE,
	password				varchar(64) NOT NULL,
	salt					TEXT default NULL,

	primary key(id)

) AUTO_INCREMENT = 1;

CREATE TABLE Product (
	id						integer(5) NOT NULL AUTO_INCREMENT,
	name					varchar(30) NOT NULL,
	comment					TEXT default NULL,

	primary key(id)

) AUTO_INCREMENT = 1;

CREATE TABLE Comments (
	id						integer(10) NOT NULL AUTO_INCREMENT,
	user_id					integer(10) NOT NULL,
	product_id				integer(5) NOT NULL,
	comment					varchar(100) default NULL,
	date					varchar (10) NOT NULL,
	
	primary key(id),
	foreign key(user_id) 	references User(id),
	foreign key(product_id) references Product(id)

) AUTO_INCREMENT = 1;

CREATE TABLE Auction (
	id						integer(5) NOT NULL AUTO_INCREMENT,
	seller_id				integer(5) NOT NULL,
	product_id				integer(5) NOT NULL,	
	price					int(5) NOT NULL,
	end_date				varchar (10) NOT NULL,
	state					varchar(10),
 
	primary key(id),
	foreign key(seller_id) 	references User(id),
	foreign key(product_id) references Product(id)
	
) AUTO_INCREMENT = 1;


CREATE TABLE Purchases (
	id						integer(5) NOT NULL AUTO_INCREMENT,
	seller_id				integer(5) NOT NULL,
	buyer_id				integer(5) NOT NULL,
	product_id				integer(5) NOT NULL,	
	price					int(5) NOT NULL,
	date					varchar (10) NOT NULL,
	state					varchar(15),
 
	primary key(id),
	foreign key(seller_id) 	references User(id),
	foreign key(buyer_id) 	references User(id),
	foreign key(product_id) references Product(id)
	
) AUTO_INCREMENT = 1;


CREATE TABLE Login (
	email					varchar(64) NOT NULL,
	login_date				varchar (30) NOT NULL,

	primary key(email)
);


INSERT INTO Product(id, name) VALUES (1, "Tablet");
INSERT INTO Product(id, name) VALUES (2, "Computer");
INSERT INTO Product(id, name) VALUES (3, "Smartphone");
INSERT INTO Product(id, name) VALUES (4, "Headphones");
INSERT INTO Product(id, name) VALUES (5, "Mouse");


INSERT INTO User (id, name, email, password) VALUES (1000, "Diogo Castro", "diogo_castro@gmail.com", "doisazul");
INSERT INTO User (name, email, password) VALUES ("Joana Oliveira", "joana_oliveira@gmail.com", "brinquedo");
INSERT INTO User (name, email, password) VALUES ("Carlos Vilas", "carlos_vilas@gmail.com", "domingo");
INSERT INTO User (name, email, password) VALUES ("Ana Maria", "ana_maria@gmail.com", "carro");
