/* INSERT books, publishers, authors and customers*/
Author: 
INSERT INTO 	Author (ID, First_name, Mid_name, Last_name) 
VALUES 		(489, 'Bill', 'A', 'Long');

Book:
INSERT INTO 	Book (ISBN, Title, Year, Category, Price)
VALUES 		('1234567890', 'The Book', '2020', 'Computer Science', '59.99');

Writes:
INSERT INTO 	Writes (Author_ID, ISBN) 
VALUES 		(489, '1234567890');

Publisher:
INSERT INTO 	Publisher (Name, Addr, ZIP_code, Phone, ISBN) 
VALUES	 	('Bridge', 'OSU', '43210', '6141231234', '1234567890');

Warehouse:
INSERT INTO 	Warehouse (Name, Addr, Zip_code, Phone) 
VALUES	 	('Sevens', 'CD', '52028', '0281231234');

Stored_in:
INSERT INTO 	Stored_in (ISBN, Warehouse_name, Quantity) 
VALUES	 	('1234567890', 'Sevens', 21);

Customer:
INSERT INTO 	Customer (Email, Username, Password) 
VALUES	 	('buckeye.489@osu.edu', 'Brutus', 'OHIO');

/* DELETE books, publishers, authors and customers*/
Author:
DELETE FROM Author WHERE ID = 489;

Book:
DELETE FROM Book WHERE ISBN = '1234567890';

Writes:
DELETE FROM Writes WHERE Author_ID = 489 AND ISBN = '1234567890';

Publisher:
DELETE FROM Publisher WHERE Name = 'Bridge';

Warehouse:
DELETE FROM Warehouse WHERE Name = 'Sevens';

Stored_in:
DELETE FROM Stored_in WHERE ISBN = '1234567890' AND Warehouse_name = 'Sevens';

Customer:
DELETE FROM Customer WHERE Email = 'buckeye.489@osu.edu';

/* DELETE examples for other entities */
Orders:
DELETE FROM Orders WHERE ID = 20;

Card:
DELETE FROM Card WHERE Card_num = '3504579991234000';

Owns:
DELETE FROM Owns WHERE Customer_email = 'buckeye.1@osu.edu' AND Card_num = '3504579991234000';

Included_in:
DELETE FROM Included_in WHERE ISBN = '0743455967' AND Order_ID = 1;
