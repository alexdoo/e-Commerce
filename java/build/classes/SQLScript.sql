CREATE DATABASE test;
USE test;

CREATE TABLE BillingInfo (
    Id INT                          NOT NULL AUTO_INCREMENT,
    CardNumber BIGINT                  NOT NULL,
    PhoneNumber BIGINT                 NOT NULL,
    Address CHAR(40)                NOT NULL,
    CardType CHAR(10)               NOT NULL,
    CardExpiryDate CHAR(8)          NOT NULL,
    PRIMARY KEY (Id),
    CHECK (Id > 0),
    CHECK (CardNumber > 1000000000000000 AND CardNumber < 9999999999999999),
    CHECK (PhoneNumber > 1000000000 AND PhoneNumber < 10000000000)
);

CREATE TABLE Seller (
    Id INT                          NOT NULL AUTO_INCREMENT,
    BillingInfoId INT               NOT NULL,
    CompanyName CHAR(20)            NOT NULL,
    PRIMARY KEY (Id),
    UNIQUE (BillingInfoId),
    FOREIGN KEY (BillingInfoId) REFERENCES BillingInfo (Id)
        ON UPDATE CASCADE,
    CHECK (Id > 0)
);

CREATE TABLE Category (
    Id INT                          NOT NULL AUTO_INCREMENT,
    CategoryName CHAR(20)           NOT NULL,
    PRIMARY KEY (Id),
    UNIQUE (CategoryName),
    CHECK (Id > 0)
);

CREATE TABLE Item (
    Id INT				NOT NULL,
    SellerId INT 			NOT NULL,
    CategoryId INT 			NOT NULL,
    Price DECIMAL			NOT NULL,
    ItemName CHAR(20)                   NOT NULL,
    Description CHAR(255),
    PRIMARY KEY (Id, SellerId),
    FOREIGN KEY (SellerId) REFERENCES Seller (Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (CategoryId) REFERENCES Category (Id)
        ON UPDATE CASCADE,
    CHECK (Id > 0 AND SellerID > 0 AND Price > 0 AND CategoryId > 0)
);

CREATE TABLE Inventory (
    ItemId INT  			NOT NULL, 
    SellerId INT 			NOT NULL,
    Quantity INT			DEFAULT 0,
    Price DECIMAL			NOT NULL,
    PRIMARY KEY (ItemId, SellerId),
    CHECK (ItemId > 0 AND SellerId > 0 AND Quantity >= 0 AND Price > 0),
    FOREIGN KEY (SellerId) REFERENCES Seller (Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (ItemId) REFERENCES Item(Id)
        ON UPDATE CASCADE
);

CREATE TABLE Customers (
    Id INT			NOT NULL AUTO_INCREMENT,
    BillingInfoId INT		NOT NULL,
    LastName CHAR(20)		NOT NULL,
    FirstName CHAR(20)		NOT NULL,
    Email CHAR(40)		NOT NULL,
    PRIMARY KEY (Id),
    UNIQUE (BillingInfoID),
    FOREIGN KEY (BillingInfoId) REFERENCES BillingInfo (Id)
        ON UPDATE CASCADE,
    CHECK (Id > 0 AND BillingInfoId > 0)
);

CREATE TABLE ShoppingCart (
    CustomerId INT			NOT NULL,
    ItemId INT 				NOT NULL,
    SellerId INT                        NOT NULL,
    Quantity INT 			DEFAULT 1,
    Price Decimal 			NOT NULL,
    TotalPrice INT 			NOT NULL,
    PRIMARY KEY (CustomerId, ItemId),
    FOREIGN KEY (CustomerId) REFERENCES Customers (Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (ItemId) REFERENCES Item (Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (SellerId) REFERENCES Seller (Id)
        ON UPDATE CASCADE,
    CHECK (CustomerId > 0 AND ItemId > 0 AND SellerId > 0 AND Quantity > 0 AND Price > 0 AND TotalPrice > 0),
    CHECK (TotalPrice >= Price), 
    CHECK ( (SELECT Quantity FROM Inventory WHERE ItemId = Inventory (ItemID) AND SellerId = Inventory (SellerId)) >= Quantity)
);

CREATE TABLE Orders (
    Id INT			NOT NULL AUTO_INCREMENT,
    CustomerId INT		NOT NULL,
    BillingInfoId INT		NOT NULL,
    OrderDate DATE		NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (CustomerId) REFERENCES Customers (Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (BillingInfoId) REFERENCES BillingInfo (Id)
        ON UPDATE CASCADE,
    CHECK (Id > 0 AND CustomerId > 0 AND BillingInfoId > 0)
);

CREATE TABLE Ordered (
    OrderId INT                 NOT NULL,
    ItemId INT                  NOT NULL,
    PRIMARY KEY (OrderId, ItemId),
    FOREIGN KEY (OrderId) REFERENCES Orders (Id),
    FOREIGN KEY (ItemID) REFERENCES Item (Id)
);

CREATE TABLE Shipment (
    CourierId INT		NOT NULL,
    OrderId INT			NOT NULL,
    Surcharge DECIMAL		NOT NULL,
    ShippingType CHAR(20)	NOT NULL,
    Address CHAR(40)		NOT NULL,
    PRIMARY KEY (OrderId, CourierId),
    FOREIGN KEY (OrderId) REFERENCES Orders (Id)
        ON UPDATE CASCADE,
    CHECK (CourierId > 0 AND OrderId > 0),
    CHECK (Surcharge >= 0)
);

CREATE TABLE Reviews (
    ItemId INT				NOT NULL,
    CustomerId INT			NOT NULL,
    SellerId INT			NOT NULL,
    Rating INT,
    Review CHAR(255),
    PRIMARY KEY (CustomerId, SellerId),
    UNIQUE (CustomerId, ItemId), 
    FOREIGN KEY (ItemId) REFERENCES Item (Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (CustomerID) REFERENCES Customers (Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (SellerId) REFERENCES Seller (Id)
        ON UPDATE CASCADE,
    CHECK (CustomerId > 0 AND ItemId > 0 AND SellerId > 0)
);

CREATE TABLE User (
    SellerId INT,
    CustomerId INT,
    Username CHAR(20)		NOT NULL,
    Password CHAR(20)		NOT NULL,
    PRIMARY KEY (Username),
    UNIQUE (CustomerId),
    FOREIGN KEY (CustomerId) REFERENCES Customers (Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (SellerId) REFERENCES Seller (Id)
        ON UPDATE CASCADE,
    CHECK (CustomerId > 0),
    CHECK (CustomerId != SellerId)
);
