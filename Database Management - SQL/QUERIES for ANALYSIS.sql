Queries from 2b. in Final Project Report.
/* a */
SELECT 	B.title 
FROM 	Book AS B, Writes AS W, Author as A 
WHERE 	B.price < 10 
 	AND B.ISBN = W.ISBN 
	AND A.ID = W.author_ID 
 	AND A.Last_name = 'Pratchett'; 
    
/* b */
SELECT 	Title, TimeT 
FROM 	Book B, Included_in I, Orders O, Card C, Owns O, Customer Cu 
WHERE 	B.ISBN = I.ISBN 
 	AND I.Order_ID = O.ID 
 	AND O.Customer_email = Cu.Email 
 	AND C.Card_num = O.card_num 
 	AND Email = 'buckeye.11@osu.edu';

/* c */
SELECT 	b.title, b.ISBN 
FROM 	book as b, warehouse as w, stored_in as s 
WHERE 	b.isbn = s.isbn 
 	AND s.warehouse_name = w.name 
GROUP BY s.isbn 
HAVING 	sum(s.quantity) < 5;

/* d */
SELECT 	Username, Title 
FROM 	Customer, Book, Author, Included_in, Owns, Card, Orders, Writes 
WHERE 	Customer.Email = Owns.Customer_email 
 	AND Owns.Card_num = Card.Card_num 
 	AND Card.Order_ID = Orders.ID 
 	AND Orders.ID = Included_in.Order_ID 
 	AND Included_in.ISBN = Book.ISBN 
	AND Author.ID = Writes.Author_ID 
	AND Book.ISBN = Writes.ISBN 
 	AND Author.Last_name = 'Pratchett';

/* e */
SELECT  	Customer.Username, SUM(Included_in.Quantity) 
FROM  	Customer, Included_in, Orders, Owns, Card 
WHERE  	Customer.Email = 'buckeye.12@osu.edu' 
 	AND Included_in.Order_ID = Card.Order_ID 
 	AND Card.Card_num = Owns.Card_num 
 	AND Owns.Customer_email = Customer.Email;

/* f */
SELECT 	max(total), email 
FROM  	(SELECT 	sum(i.quantity) as total, cus.email 
 	FROM 	included_in AS i, orders AS o, owns AS ow, card AS c, customer AS cus 
 	WHERE 	i.order_ID = o.id 
 		AND o.id = c.order_ID 
 		AND c.card_num = ow.card_num 
 		AND ow.customer_email = cus.email 
 	GROUP BY cus.email);

/* g */
SELECT 	name 
FROM 	TotalQuantityByWarehouse 
WHERE 	sumOfQuantity IN 
	(SELECT min(sumOfQuantity) 
	FROM TotalQuantityByWarehouse); 

/* h */
SELECT 	b.title, p.name 
FROM 	Book AS b, Publisher AS p 
WHERE 	b.isbn = p.isbn 
 	AND p.name = 'Harper';

/* i */
SELECT 	title 
FROM 	(SELECT 	title, max(total) 
 	FROM 	(SELECT 	sum(i.quantity) AS total, cus.email, title 
 		FROM 	included_in AS i, orders AS o, owns AS ow, card AS c, customer AS cus, Book 
 		WHERE 	i.order_ID = o.id 
 			AND o.id = c.order_ID 
 			AND c.card_num = ow.card_num 
 			AND ow.customer_email = cus.email 
 			AND i.ISBN = Book.ISBN 
 		GROUP BY cus.email));

/* j */
SELECT * FROM amountForEachUser;

/* k */
SELECT 	Customer.username, Customer.Email 
FROM 	amountForEachOrder,Customer,Owns, Card 
WHERE 	amountForEachOrder.order_id = Card.Order_ID 
 	AND Card.Card_num = Owns.Card_num 
 	AND Owns.Customer_email = Customer.Email 
GROUP BY username 
HAVING 	amount > (SELECT avg(amount) FROM amountForEachUser);

/* l */
SELECT 	b.title, sum(i.quantity) as sumOfBook 
FROM 	book AS b, Included_in AS i 
WHERE 	b.isbn = i.isbn 
GROUP By b.Title 
ORDER BY sumOfBook desc; 

/* m */
SELECT 	b.title, sum(i.quantity*b.price) as sumMoneyOfBook 
From 	book AS b, Included_in AS i 
Where 	b.isbn = i.isbn 
GROUP BY b.Title 
ORDER BY sumMoneyOfBook desc;

/* n */
SELECT 	au.First_name, au.Last_name, max(sumOfBook) 
FROM 	(SELECT 	b.isbn as isb, sum(i.quantity) as sumOfBook 
 	From 	book as b, Included_in as i 
 	WhERE 	b.isbn = i.isbn 
 	GROUP By b.Title 
 	Order by 	sumOfBook desc), Author as au, writes as w 
WHERE 	au.id = w.Author_ID 
 	AND w.ISBN=isb;

/* o */
SELECT 	au.First_name, au.Last_name, max(sumOfBook) 
FROM 	Author AS au, writes AS w, 
 		(SELECT 	b.isbn AS isb, sum(i.quantity*b.Price) AS sumOfBook 
 		FROM 	book AS b, Included_in AS i, Author AS au, writes AS w 
 		WHERE 	b.isbn = i.isbn 
 			AND au.id=w.author_id 
 			AND w.isbn=b.isbn 
 		GROUP By au.id) 
WHERE 	au.id = w.Author_ID 
 	AND w.ISBN=isb;

/* p */
SELECT 	distinct(C.email), C.username 
FROM 	author AS a, writes AS w, book AS b, included_in AS i, orders AS o, card AS cd, owns, Customer AS c 
WHERE 	a.id=w.author_id 
 	AND w.isbn=b.isbn 
 	AND b.isbn=i.isbn 
 	AND i.order_id=o.id 
 	AND o.id=cd.order_id 
 	AND cd.card_num=owns.card_num 
 	AND owns.customer_email=c.email 
 	AND a.id IN
 		(SELECT 	id 
 		FROM 	(SELECT 	au.id,max(sumOfBook) 
 			FROM 	Author AS au, writes AS w, 
 				(SELECT 	b.isbn AS isb, sum(i.quantity*b.Price) AS sumOfBook 
 				From 	book as b, Included_in as i, Author as au, writes as w 
 				WHERE 	b.isbn = i.isbn AND au.id=w.author_id AND w.isbn=b.isbn 
 				GROUP By au.id) 
 		WHERE 	au.id = w.Author_ID and w.ISBN=isb));

/* q */
SELECT 	distinct(a.id), a.first_name, a.last_name 
FROM 	author as a, writes as w, book as b, included_in as i, orders as o, card as cd, owns, Customer as c 
WHERE 	a.id=w.author_id 
 	AND w.isbn=b.isbn 
 	AND b.isbn=i.isbn 
 	AND i.order_id=o.id 
 	AND o.id=cd.order_id 
 	AND cd.card_num=owns.card_num 
 	AND owns.customer_email=c.email 
 	AND c.username IN 
 		(SELECT 	Customer.username 
 		FROM 	amountForEachOrder,Customer,Owns, Card 
 		WHERE 	amountForEachOrder.order_id = Card.Order_ID 
 			AND Card.Card_num = Owns.Card_num 
 			AND Owns.Customer_email = Customer.Email 
 		GROUP BY username 
 		HAVING 	amount > (SELECT avg(amount) FROM amountForEachUser));
