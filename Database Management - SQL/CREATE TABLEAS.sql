.open bookstore.db

CREATE TABLE Author (
	ID		INTEGER PRIMARY KEY,
	First_name	VARCHAR(45)	NOT NULL,
    	Mid_name	VARCHAR(45),
       	Last_name	VARCHAR(45)	NOT NULL
);

CREATE TABLE Book (
	ISBN		VARCHAR(15)	NOT NULL,
	Title		VARCHAR(45)	NOT NULL,
	Year		INT		NOT NULL,
	Category		VARCHAR(45)	NOT NULL,
	Price		DECIMAL(5,2)	NOT NULL,
       	PRIMARY KEY (ISBN)
);

CREATE TABLE Writes (
	AUTHOR_ID	INT		NOT NULL,
	ISBN		VARCHAR(15)	NOT NULL,
	PRIMARY KEY (Author_ID, ISBN),
	FOREIGN KEY(AUTHOR_ID) REFERENCES AUTHOR(ID),
	FOREIGN KEY(ISBN) REFERENCES BOOK(ISBN)
);

CREATE TABLE Publisher (
	Name		VARCHAR(45)	NOT NULL,
	Addr		VARCHAR(45)	NOT NULL,
	ZIP_code		CHAR(5)		NOT NULL,
	PHONE		VARCHAR (15)	NOT NULL,
	ISBN		VARCHAR (15)	NOT NULL,
	PRIMARY KEY (Name, ISBN),
	FOREIGN KEY (ISBN) REFERENCES Book (ISBN)
);

Create Table Warehouse (
	Name		VARCHAR(45)	NOT NULL,
	Addr		VARCHAR(45)	NOT NULL,
	Zip_Code		CHAR(5)		NOT NULL,
	Phone		VARCHAR (15)	NOT NULL,
	PRIMARY KEY(Name)
);

CREATE TABLE Stored_in (
	ISBN		VARCHAR(15)	NOT NULL,
	Warehouse_Name	VARCHAR(45)	NOT NULL,
	Quantity		INT		NOT NULL,
	PRIMARY KEY (ISBN, Warehouse_Name),
	FOREIGN KEY (ISBN) REFERENCES Book(ISBN),
	FOREIGN KEY (Warehouse_Name) REFERENCES Warehouse(Name)
);

CREATE TABLE Customer (
	Email		VARCHAR(45)	NOT NULL,
	Username	VARCHAR(45)	NOT NULL,
	Password		VARCHAR(45)	NOT NULL,
	PRIMARY KEY(Email)
);

CREATE TABLE Orders (
	ID		INTEGER PRIMARY KEY,
	TimeT		DATETIME	NOT NULL,
	Phone		VARCHAR (15)	NOT NULL,
	First_name	VARCHAR (45)	NOT NULL,
	Last_name	VARCHAR (45)	NOT NULL,
	Shipping_zip	VARCHAR (45)	NOT NULL,
	Shipping_addr	VARCHAR (45)	NOT NULL
);

CREATE TABLE Card (
	Card_num	CHAR(16)		NOT NULL,
	Security_code	CHAR(3)		NOT NULL,
	Country		VARCHAR(45)	NOT NULL,
	Exp_date		CHAR(5)		NOT NULL,
	Billing_zip	CHAR(5)		NOT NULL,
	Billing_addr	VARCHAR(45)	NOT NULL,
	First_name	VARCHAR(45)	NOT NULL,
	Last_name	VARCHAR(45)	NOT NULL,
	Phone		VARCHAR (15)	NOT NULL,
	Order_ID		INT		NOT NULL,
	PRIMARY KEY (Card_num),
	FOREIGN KEY (ORDER_ID) REFERENCES Orders(ID)
);

CREATE TABLE Owns (
	Customer_email	VARCHAR(45)	NOT NULL,
	Card_num	CHAR(16)		NOT NULL,
	PRIMARY KEY (Customer_email),
	FOREIGN KEY (Customer_email) REFERENCES Customer (Email),
	FOREIGN KEY (Card_num) REFERENCES Card (Card_num)
);

CREATE TABLE Included_in (
	ISBN		VARCHAR(15)	NOT NULL,
	Order_ID		INT		NOT NULL,
	Quantity		INT		NOT NULL,
	PRIMARY KEY (ISBN, Order_ID),
	FOREIGN KEY (ISBN) REFERENCES Book (ISBN),
	FOREIGN KEY (Order_ID) REFERENCES Orders (ID)
);

CREATE INDEX author_lastname ON Author(Last_name);

CREATE INDEX book_title ON Book(Title);

CREATE VIEW amountForEachOrder(amount, order_id) AS 
	SELECT 		sum(B.Price*I.Quantity), I.Order_ID 
	FROM 		Book AS B, Included_in as I 
	WHERE 		B.ISBN = I.ISBN 
	GROUP BY 	I.Order_ID;

CREATE VIEW amountForEachUser(amount, username) AS 
 	SELECT 	sum(amount),Customer.Username 
 	FROM 	amountForEachOrder,Customer,Owns, Card 
 	WHERE 	amountForEachOrder.order_id = Card.Order_ID 
 		AND Card.Card_num = Owns.Card_num 
 		AND Owns.Customer_email = Customer.Email 
 	GROUP BY username;

CREATE VIEW TotalQuantityByWarehouse (Name, SumOfQuantity) AS 
	SELECT 	W.Name, sum(S.Quantity) 
	FROM 	Book AS B, Stored_in AS S, Warehouse AS W 
	WHERE	B.ISBN = S.ISBN 
 		AND S.Warehouse_name = W.Name 
	GROUP BY W.Name;