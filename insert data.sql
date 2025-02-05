-- Insert sample items
INSERT INTO Item (itemID, itemName, itemPrice, itemDescription) VALUES 
(1, 'Laptop', 1500.00, 'High performance laptop for gaming and work');

INSERT INTO Item (itemID, itemName, itemPrice, itemDescription) VALUES 
(2, 'Smartphone', 800.00, 'Latest model smartphone with advanced features');

INSERT INTO Item (itemID, itemName, itemPrice, itemDescription) VALUES 
(3, 'Headphones', 200.00, 'Noise canceling wireless headphones');

INSERT INTO Item (itemID, itemName, itemPrice, itemDescription) VALUES 
(4, 'Tablet', 600.00, 'Lightweight and portable tablet with high resolution display');

INSERT INTO Item (itemID, itemName, itemPrice, itemDescription) VALUES 
(5, 'Smartwatch', 250.00, 'Water resistant smartwatch with fitness tracking features');

INSERT INTO Item (itemID, itemName, itemPrice, itemDescription) VALUES 
(6, 'Camera', 1200.00, 'Professional DSLR camera with multiple lenses');

INSERT INTO Item (itemID, itemName, itemPrice, itemDescription) VALUES 
(7, 'Gaming Console', 500.00, 'Next gen gaming console with 4K support');

INSERT INTO Item (itemID, itemName, itemPrice, itemDescription) VALUES 
(8, 'Wireless Charger', 50.00, 'Fast wireless charger for all compatible devices');

-- Insert sample persons
INSERT INTO Person (userName, firstName, lastName, gender, password, birthdate, phone, points) VALUES 
('john_doe', 'John', 'Doe', 'Male', 'pass1234', TO_DATE('1990-05-15', 'YYYY-MM-DD'), '1234567890', 100);

INSERT INTO Person (userName, firstName, lastName, gender, password, birthdate, phone, points) VALUES 
('jane_smith', 'Jane', 'Smith', 'Female', 'securepass', TO_DATE('1992-08-21', 'YYYY-MM-DD'), '0987654321', 250);

INSERT INTO Person (userName, firstName, lastName, gender, password, birthdate, phone, points) VALUES 
('alice_wonder', 'Alice', 'Wonderland', 'Female', 'alicepass', TO_DATE('1989-12-10', 'YYYY-MM-DD'), '1112223333', 500);

INSERT INTO Person (userName, firstName, lastName, gender, password, birthdate, phone, points) VALUES 
('bob_marley', 'Bob', 'Marley', 'Male', 'reggae123', TO_DATE('1985-06-05', 'YYYY-MM-DD'), '2223334444', 320);

INSERT INTO Person (userName, firstName, lastName, gender, password, birthdate, phone, points) VALUES 
('charlie_brown', 'Charlie', 'Brown', 'Male', 'charlie123', TO_DATE('1993-11-22', 'YYYY-MM-DD'), '3334445555', 280);

INSERT INTO Person (userName, firstName, lastName, gender, password, birthdate, phone, points) VALUES 
('diana_prince', 'Diana', 'Prince', 'Female', 'amazonian', TO_DATE('1990-04-01', 'YYYY-MM-DD'), '4445556666', 400);

-- Insert sample wishlist items
INSERT INTO wishListItem (userName, itemID, remaining) VALUES 
('john_doe', 1, 500.00);

INSERT INTO wishListItem (userName, itemID, remaining) VALUES 
('jane_smith', 2, 300.00);

INSERT INTO wishListItem (userName, itemID, remaining) VALUES 
('john_doe', 3, 100.00);

INSERT INTO wishListItem (userName, itemID, remaining) VALUES 
('jane_smith', 4, 400.00);

INSERT INTO wishListItem (userName, itemID, remaining) VALUES 
('alice_wonder', 5, 50.00);

INSERT INTO wishListItem (userName, itemID, remaining) VALUES 
('bob_marley', 6, 800.00);

INSERT INTO wishListItem (userName, itemID, remaining) VALUES 
('charlie_brown', 7, 200.00);

INSERT INTO wishListItem (userName, itemID, remaining) VALUES 
('diana_prince', 8, 30.00);

-- Insert sample friendships
INSERT INTO personFriends (personUserName, friendUserName, timeStamp, status) VALUES 
('john_doe', 'jane_smith', SYSTIMESTAMP, 'Accepted');

INSERT INTO personFriends (personUserName, friendUserName, timeStamp, status) VALUES 
('jane_smith', 'john_doe', SYSTIMESTAMP, 'Accepted');

INSERT INTO personFriends (personUserName, friendUserName, timeStamp, status) VALUES 
('john_doe', 'alice_wonder', SYSTIMESTAMP, 'Accepted');

INSERT INTO personFriends (personUserName, friendUserName, timeStamp, status) VALUES 
('alice_wonder', 'john_doe', SYSTIMESTAMP, 'Accepted');

INSERT INTO personFriends (personUserName, friendUserName, timeStamp, status) VALUES 
('jane_smith', 'bob_marley', SYSTIMESTAMP, 'Pending');

INSERT INTO personFriends (personUserName, friendUserName, timeStamp, status) VALUES 
('bob_marley', 'jane_smith', SYSTIMESTAMP, 'Pending');

INSERT INTO personFriends (personUserName, friendUserName, timeStamp, status) VALUES 
('charlie_brown', 'diana_prince', SYSTIMESTAMP, 'Accepted');

INSERT INTO personFriends (personUserName, friendUserName, timeStamp, status) VALUES 
('diana_prince', 'charlie_brown', SYSTIMESTAMP, 'Accepted');

-- Insert sample credit cards
INSERT INTO CreditCard (creditCardNumber, CVV, valid, holderName, userName) VALUES 
('1234123412341234', 123, TO_DATE('2027-06-30', 'YYYY-MM-DD'), 'John Doe', 'john_doe');

INSERT INTO CreditCard (creditCardNumber, CVV, valid, holderName, userName) VALUES 
('5678567856785678', 456, TO_DATE('2028-09-15', 'YYYY-MM-DD'), 'Jane Smith', 'jane_smith');

INSERT INTO CreditCard (creditCardNumber, CVV, valid, holderName, userName) VALUES 
('1111222233334444', 789, TO_DATE('2026-08-25', 'YYYY-MM-DD'), 'Alice Wonderland', 'alice_wonder');

INSERT INTO CreditCard (creditCardNumber, CVV, valid, holderName, userName) VALUES 
('5555666677778888', 234, TO_DATE('2027-12-10', 'YYYY-MM-DD'), 'Bob Marley', 'bob_marley');

INSERT INTO CreditCard (creditCardNumber, CVV, valid, holderName, userName) VALUES 
('9999000011112222', 567, TO_DATE('2028-07-19', 'YYYY-MM-DD'), 'Charlie Brown', 'charlie_brown');

INSERT INTO CreditCard (creditCardNumber, CVV, valid, holderName, userName) VALUES 
('3333444455556666', 890, TO_DATE('2029-01-05', 'YYYY-MM-DD'), 'Diana Prince', 'diana_prince');

-- Insert sample notifications
INSERT INTO Notification (notificationText, timeStamp, userName) VALUES 
('You have a new message.', SYSTIMESTAMP, 'john_doe');

INSERT INTO Notification (notificationText, timeStamp, userName) VALUES 
('Your order has been shipped.', SYSTIMESTAMP, 'jane_smith');

INSERT INTO Notification (notificationText, timeStamp, userName) VALUES 
('You have a friend request.', SYSTIMESTAMP, 'alice_wonder');

INSERT INTO Notification (notificationText, timeStamp, userName) VALUES 
('Your wishlist item is on sale.', SYSTIMESTAMP, 'bob_marley');

INSERT INTO Notification (notificationText, timeStamp, userName) VALUES 
('You have a new comment on your post.', SYSTIMESTAMP, 'charlie_brown');

INSERT INTO Notification (notificationText, timeStamp, userName) VALUES 
('Your profile has been updated.', SYSTIMESTAMP, 'diana_prince');

COMMIT;


select w.itemid, w.remaining, I.ITEMNAME,I.ITEMPRICE, I.ITEMDESCRIPTION
from wishlistitem w, item i  
where w.ITEMID = i.ITEMID and w.username =  'john_doe'