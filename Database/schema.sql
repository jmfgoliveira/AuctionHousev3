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
	price					int(5) NOT NULL,
	quantity				int(5) NOT NULL,
	owner_id				integer(5) NOT NULL,
	description				TEXT default NULL,

	primary key(id),
	foreign key(owner_id) 	references User(id)

) AUTO_INCREMENT = 1;

CREATE TABLE Auction (
	id						integer(5) NOT NULL AUTO_INCREMENT,
	product_id				integer(5) NOT NULL,
	seller_id				integer(5) NOT NULL,		
	buyer_id				integer(5) NOT NULL,		
	end_date				DATETIME NOT NULL,
	auction_state 			varchar(30) NOT NULL,
 
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





INSERT INTO User (id, name, email, password) VALUES (1000, "Diogo Castro", "diogo_castro@gmail.com", "doisazul");
INSERT INTO User (name, email, password) VALUES ("Joana Oliveira", "joana_oliveira@gmail.com", "brinquedo");
INSERT INTO User (name, email, password) VALUES ("Carlos Vilas", "carlos_vilas@gmail.com", "domingo");
INSERT INTO User (name, email, password) VALUES ("Ana Maria", "ana_maria@gmail.com", "carro");

INSERT INTO Product(id, name, price, quantity, owner_id) VALUES (10, "bola", 100, 1, 1000);
INSERT INTO Product(name, price, quantity, owner_id) VALUES ("carro", 100, 1, 1000);