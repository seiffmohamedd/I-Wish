CREATE TABLE Item (
    itemID INT PRIMARY KEY,
    itemName VARCHAR(20),
    itemPrice NUMBER(8,2),
    itemDescription CLOB
);

CREATE TABLE Person (
    userName VARCHAR(20) PRIMARY KEY,
    firstName VARCHAR(20),
    lastName VARCHAR(20),
    gender VARCHAR(6),
    password VARCHAR(20),
    birthdate DATE,
    phone VARCHAR(15),
    points NUMBER(8,2) default 0,
    wishListID INT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1)
);

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
    PRIMARY KEY (userName, itemID, friendUserName),
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

CREATE TABLE Notification (
    notificationID INT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    timeStamp TIMESTAMP,
    userName VARCHAR(20),
    PRIMARY KEY (notificationID),
    FOREIGN KEY (userName) REFERENCES Person(userName) ON DELETE CASCADE
);

