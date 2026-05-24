CREATE TABLE Accounts (
    userName VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL  
);

CREATE TABLE Seller (
    sellerID SERIAL PRIMARY KEY,
    userName VARCHAR(50) UNIQUE REFERENCES Accounts(userName)
);

CREATE TABLE Buyer (
    buyerID SERIAL PRIMARY KEY,
    userName VARCHAR(50) UNIQUE REFERENCES Accounts(userName),
    address VARCHAR(200)
);

CREATE TABLE Products (
    productID SERIAL PRIMARY KEY,
    productName VARCHAR(100) UNIQUE NOT NULL,
    serialNumber VARCHAR(100) UNIQUE NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(50) NOT NULL,
    sellerUserName VARCHAR(50) REFERENCES Seller(userName)
);

CREATE TABLE buyerCart (
    cartID SERIAL PRIMARY KEY,
    buyerUserName VARCHAR(50) UNIQUE REFERENCES Buyer(userName),
    cartSum DECIMAL(10,2) DEFAULT 0
);


CREATE TABLE Cart_Products (
    cartID INT,
    productName VARCHAR(100),
    quantity INT DEFAULT 1,
    PRIMARY KEY (cartID, productName),
    FOREIGN KEY (cartID) REFERENCES buyerCart(cartID) ON DELETE CASCADE,
    FOREIGN KEY (productName) REFERENCES Products(productName) ON DELETE CASCADE
);

CREATE TABLE Cart_History (
    historyID SERIAL PRIMARY KEY,
    buyerUserName VARCHAR(50) REFERENCES Buyer(userName),
    productList TEXT NOT NULL,
    totalPaid DECIMAL(10,2) NOT NULL,
    datePaid TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SELECT * FROM Products;
SELECT * FROM Accounts;
SELECT * FROM buyerCart;
SELECT * FROM Cart_Products;
SELECT * FROM Seller;
SELECT * FROM Buyer;
SELECT * FROM Cart_History;

