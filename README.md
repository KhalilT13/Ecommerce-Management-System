# E-commerce Management System

A console-based e-commerce management system built with Java, PostgreSQL, and JDBC.

## Features

- Buyer and seller management
- Product management
- Shopping cart functionality
- Order history
- Product categories
- PostgreSQL database connection using JDBC

## Technologies Used

- Java
- PostgreSQL
- JDBC
- SQL
- Object-Oriented Programming

## How to Run

1. Create a PostgreSQL database.
2. Run the `tablesFile.sql` file to create the tables.
3. Open `DataBaseManager.java`.
4. Change the database name, username, and password:
5. Make sure `postgresql-42.7.2.jar` from the `lib` folder is added to the project libraries.
6. Run `Main.java`.

```java
private static String url = "jdbc:postgresql://localhost:5432/Your_DB_Name";
private static String user = "postgres";
private static String password = "Your_Password";

## Author

Khalil Talhami
