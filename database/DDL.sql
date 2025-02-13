CREATE TABLE Item (
    itemID INT PRIMARY KEY,
    itemName VARCHAR(20),
    itemPrice NUMBER(8,2),
    itemDescription CLOB
);

CREATE SEQUENCE person_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE Person (
    userName VARCHAR2(20) PRIMARY KEY,
    firstName VARCHAR2(20),
    lastName VARCHAR2(20),
    gender VARCHAR2(6),
    password VARCHAR2(20),
    birthdate DATE,
    phone VARCHAR2(15),
    points NUMBER(8,2) DEFAULT 0,
    wishListID NUMBER
);




CREATE OR REPLACE TRIGGER person_trigger
BEFORE INSERT ON Person
FOR EACH ROW
BEGIN
    :NEW.wishListID := person_seq.NEXTVAL;
END;




CREATE TABLE wishListItem (
    userName VARCHAR(20),
    itemID INT,
    remaining NUMBER(8,2),
    PRIMARY KEY (userName, itemID),
    FOREIGN KEY (userName) REFERENCES Person(userName) ON DELETE CASCADE,
    FOREIGN KEY (itemID) REFERENCES Item(itemID) ON DELETE CASCADE
);


CREATE TABLE personFriends (
    personUserName VARCHAR(20),
    friendUserName VARCHAR(20),
    timeStamp TIMESTAMP,
    status VARCHAR(10),
    PRIMARY KEY (personUserName, friendUserName,timeStamp),
    FOREIGN KEY (personUserName) REFERENCES Person(userName) ON DELETE CASCADE,
    FOREIGN KEY (friendUserName) REFERENCES Person(userName) ON DELETE CASCADE
);


CREATE TABLE wishListItemContribute (
    userName VARCHAR(20),
    itemID INT,
    friendUserName VARCHAR(20),
    contributeAmount NUMBER(8,2),
    timeStamp TIMESTAMP Default CURRENT_TIMESTAMP,
    PRIMARY KEY (userName, itemID, friendUserName,timeStamp),
    FOREIGN KEY (userName) REFERENCES Person(userName) ON DELETE CASCADE,
    FOREIGN KEY (itemID) REFERENCES Item(itemID) ON DELETE CASCADE,
    FOREIGN KEY (friendUserName) REFERENCES Person(userName) ON DELETE CASCADE
);

CREATE TABLE CreditCard (
    creditCardNumber VARCHAR(16) PRIMARY KEY,
    CVV INT,
    valid DATE,
    holderName VARCHAR(50),
    userName VARCHAR(20),
    FOREIGN KEY (userName) REFERENCES Person(userName) ON DELETE CASCADE
);


CREATE SEQUENCE notification_seq START WITH 1 INCREMENT BY 1;


CREATE TABLE Notification (
    notificationID NUMBER,
    timeStamp TIMESTAMP,
    userName VARCHAR2(20),
    PRIMARY KEY (notificationID),
    FOREIGN KEY (userName) REFERENCES Person(userName) ON DELETE CASCADE
);


CREATE OR REPLACE TRIGGER notification_trigger
BEFORE INSERT ON Notification
FOR EACH ROW
BEGIN
    :NEW.notificationID := notification_seq.NEXTVAL;
END;

