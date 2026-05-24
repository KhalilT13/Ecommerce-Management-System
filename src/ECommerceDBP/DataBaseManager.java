package ECommerceDBP;

import java.math.BigDecimal;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataBaseManager {
    private static String url = "jdbc:postgresql://localhost:5432/Your_DB_Name";
    private static String user = "postgres";
    private static String password = "Your_Password";

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static void insertSeller(Seller seller) {
        String insertAccountSQL = "INSERT INTO Accounts (userName, password, type) VALUES (?, ?, 'Seller')";
        String insertSellerSQL = "INSERT INTO Seller (userName) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement stmtAccount = conn.prepareStatement(insertAccountSQL);
                    PreparedStatement stmtSeller = conn.prepareStatement(insertSellerSQL)
            ) {
                stmtAccount.setString(1, seller.getUserName());
                stmtAccount.setString(2, seller.getPassword());
                stmtAccount.executeUpdate();


                stmtSeller.setString(1, seller.getUserName());
                stmtSeller.executeUpdate();

                conn.commit();
                System.out.println("Seller inserted successfully into the database.");
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Error inserting seller, transaction rolled back:");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Database connection error:");
            e.printStackTrace();
        }
    }


    public static void insertProductToSeller(Product product, Seller seller) {
        String sql = "INSERT INTO Products (productName, price, serialNumber, category, sellerUserName) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductName());
            stmt.setBigDecimal(2, product.getDecimalPrice());  // Assuming price as BigDecimal
            stmt.setString(3, product.getSerialNumber());
            stmt.setString(4, product.getProductCategory());   // Category as String
            stmt.setString(5, seller.getUserName());           // FK to Seller.userName

            stmt.executeUpdate();
            System.out.println("Product inserted successfully into the database.");

        } catch (SQLException e) {
            System.err.println("Error inserting product into database:");
            e.printStackTrace();
        }
    }


    public static void insertBuyer(Buyer buyer) {
        String insertAccountSQL = "INSERT INTO Accounts (userName, password, type) VALUES (?, ?, 'Buyer')";
        String insertBuyerSQL = "INSERT INTO Buyer (userName, address) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement stmtAccount = conn.prepareStatement(insertAccountSQL);
                    PreparedStatement stmtBuyer = conn.prepareStatement(insertBuyerSQL)
            ) {
                stmtAccount.setString(1, buyer.getUserName());
                stmtAccount.setString(2, buyer.getPassword());
                stmtAccount.executeUpdate();


                stmtBuyer.setString(1, buyer.getUserName());
                stmtBuyer.setString(2, buyer.getAddress());
                stmtBuyer.executeUpdate();

                conn.commit();
                System.out.println("Buyer inserted successfully into the database.");
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Error inserting buyer, transaction rolled back:");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Database connection error:");
            e.printStackTrace();
        }
    }


    public static void addProductToBuyersCart(Buyer buyer, Product product) {
        String getCartIDSQL = "SELECT cartID FROM buyerCart WHERE buyerUserName = ?";
        String createCartSQL = "INSERT INTO buyerCart (buyerUserName, cartSum) VALUES (?, 0) RETURNING cartID";
        String existsSQL = "SELECT 1 FROM Cart_Products WHERE cartID = ? AND productName = ?";
        String insertSQL = "INSERT INTO Cart_Products (cartID, productName, quantity) VALUES (?, ?, 1)";
        String updateQtySQL = "UPDATE Cart_Products SET quantity = quantity + 1 WHERE cartID = ? AND productName = ?";
        String updateCartSumSQL = "UPDATE buyerCart SET cartSum = cartSum + ? WHERE cartID = ?";

        int cartID;
        BigDecimal productTotal = product.getDecimalPrice().add(product.getExtraPackaging());

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);


            try (PreparedStatement getCartStmt = conn.prepareStatement(getCartIDSQL)) {
                getCartStmt.setString(1, buyer.getUserName());
                ResultSet rs = getCartStmt.executeQuery();

                if (rs.next()) {
                    cartID = rs.getInt("cartID");
                } else {

                    try (PreparedStatement createCartStmt = conn.prepareStatement(createCartSQL)) {
                        createCartStmt.setString(1, buyer.getUserName());
                        ResultSet newCart = createCartStmt.executeQuery();
                        if (newCart.next()) {
                            cartID = newCart.getInt("cartID");
                        } else {
                            throw new SQLException("Failed to create cart for buyer: " + buyer.getUserName());
                        }
                    }
                }
            }


            boolean productExists;
            try (PreparedStatement checkStmt = conn.prepareStatement(existsSQL)) {
                checkStmt.setInt(1, cartID);
                checkStmt.setString(2, product.getProductName());
                ResultSet rs = checkStmt.executeQuery();
                productExists = rs.next();
            }


            if (productExists) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQtySQL)) {
                    updateStmt.setInt(1, cartID);
                    updateStmt.setString(2, product.getProductName());
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                    insertStmt.setInt(1, cartID);
                    insertStmt.setString(2, product.getProductName());
                    insertStmt.executeUpdate();
                }
            }


            try (PreparedStatement updateCartStmt = conn.prepareStatement(updateCartSumSQL)) {
                updateCartStmt.setBigDecimal(1, productTotal);
                updateCartStmt.setInt(2, cartID);
                updateCartStmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Product added and cart sum updated for buyer: " + buyer.getUserName());

        } catch (SQLException e) {
            System.err.println("Error adding product to cart:");
            e.printStackTrace();
        }
    }


    public static void deleteBuyerCart(Buyer buyer) {
        String getCartIDSQL = "SELECT cartID FROM buyerCart WHERE buyerUserName = ?";
        String deleteCartProductsSQL = "DELETE FROM Cart_Products WHERE cartID = ?";
        String deleteCartSQL = "DELETE FROM buyerCart WHERE cartID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            int cartID;


            try (PreparedStatement getCartStmt = conn.prepareStatement(getCartIDSQL)) {
                getCartStmt.setString(1, buyer.getUserName());
                ResultSet rs = getCartStmt.executeQuery();
                if (rs.next()) {
                    cartID = rs.getInt("cartID");
                } else {
                    System.out.println("No cart found for buyer: " + buyer.getUserName());
                    return;
                }
            }


            try (PreparedStatement deleteCartProductsStmt = conn.prepareStatement(deleteCartProductsSQL)) {
                deleteCartProductsStmt.setInt(1, cartID);
                deleteCartProductsStmt.executeUpdate();
            }

            try (PreparedStatement deleteCartStmt = conn.prepareStatement(deleteCartSQL)) {
                deleteCartStmt.setInt(1, cartID);
                deleteCartStmt.executeUpdate();
            }

            conn.commit();

            System.out.println("Cart successfully deleted for buyer: " + buyer.getUserName());

        } catch (SQLException e) {
            System.err.println("Error deleting buyer's cart:");
            e.printStackTrace();
        }
    }

    public static void addCartToHistory(Buyer buyer) {
        if (buyer.getCart() == null || buyer.getCart().getProducts() == null || buyer.getCart().getProducts().length == 0) {
            System.out.println("No cart to archive for buyer: " + buyer.getUserName());
            return;
        }


        Map<String, Integer> productCountMap = new LinkedHashMap<>();
        for (Product p : buyer.getCart().getProducts()) {
            if (p != null) {
                String name = p.getProductName();
                productCountMap.put(name, productCountMap.getOrDefault(name, 0) + 1);
            }
        }

        StringBuilder productList = new StringBuilder();
        for (Map.Entry<String, Integer> entry : productCountMap.entrySet()) {
            productList.append(entry.getKey())
                    .append(" x")
                    .append(entry.getValue())
                    .append(", ");
        }

        if (productList.length() > 0) {
            productList.setLength(productList.length() - 2);
        }

        BigDecimal totalPaid = buyer.getBigDecemalCartTotal();

        String sql = "INSERT INTO Cart_History (buyerUserName, productList, totalPaid) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, buyer.getUserName());
            stmt.setString(2, productList.toString());
            stmt.setBigDecimal(3, totalPaid);

            stmt.executeUpdate();
            System.out.println("Cart successfully archived for buyer: " + buyer.getUserName());

        } catch (SQLException e) {
            System.err.println("Error archiving cart to history:");
            e.printStackTrace();
        }
    }

}


