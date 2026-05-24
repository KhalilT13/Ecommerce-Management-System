package ECommerceDBP;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import static ECommerceDBP.Main.changeToInt;


public class Admin implements IAdmin {
    static OrdersHistory ordersHistory;
    static LocalDate currentDate;
    static Seller[] sellers = new Seller[0];
    static Buyer[] buyers = new Buyer[0];
    static String name;
    static String password;
    static String CountryName;
    static String CityName;
    static String StreetName;
    static String BuildingNumber;
    static String userInput;
    static int sellerLen = 0;
    static int buyerLen = 0;

    public static void AddSeller(Scanner s1) {
        System.out.println("Enter username : ");
        while (true) {
            try {
                name = s1.nextLine();
                validUsername(name);
                break;

            } catch (WrongInput e) {
                System.out.println(e.getMessage());
            }

        }
        while (SellerExists(sellers, name)) {
            System.out.println("Username Exists , Enter a new one :");
            while (true) {
                try {
                    name = s1.nextLine();
                    validUsername(name);
                    break;

                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }
            }
        }


        System.out.println("enter password : ");
        while (true) {
            try {
                password = s1.nextLine();
                validPassword(password);
                break;

            } catch (WrongInput e) {
                System.out.println(e.getMessage());
            }
        }
        if (sellerLen == 0) {
            sellers = new Seller[1];
            sellers[0] = new Seller(name, password);
            sellerLen++;
        } else if (sellerLen < sellers.length) {
            for (int i = 0; i < sellers.length; i++) {
                if (sellers[i] == null) {
                    sellers[i] = new Seller(name, password);
                    sellerLen++;
                    break;
                }
            }
        } else {
            sellers = MakeNewSellersArr(sellers, sellers.length, name, password);

        }
        Seller current = new Seller(name, password);
        DataBaseManager.insertSeller(current);
    }

    public static void validUsername(String username) throws WrongInput {
        if (username.isEmpty() || username.contains(" ")) {
            throw new WrongInput("Username cannot contain spaces or be empty ");
        }
    }

    public static void AddBuyer(Scanner s1) {
        System.out.println("Enter username : ");
        while (true) {
            try {
                name = s1.nextLine();
                validUsername(name);
                break;
            } catch (WrongInput e) {
                System.out.println(e.getMessage());
            }
        }
        while (BuyerExists(buyers, name)) {
            System.out.println("Username Exists , Enter a new one :");
            while (true) {
                try {
                    name = s1.nextLine();
                    validUsername(name);
                    break;

                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("Enter password : ");
        while (true) {
            try {
                password = s1.nextLine();
                validPassword(password);
                break;

            } catch (WrongInput e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Enter Country : ");
        CountryName = s1.nextLine();
        System.out.println("Enter City Name :");
        CityName = s1.nextLine();
        System.out.println("Enter Street Name :");
        StreetName = s1.nextLine();
        System.out.println("Enter Building Number :");
        BuildingNumber = s1.nextLine();

        Address address = new Address(CountryName, CityName, StreetName, BuildingNumber);

        if (buyerLen == 0) {
            buyers = new Buyer[1];
            buyers[0] = new Buyer(name, password, address);
            buyerLen++;
        } else if (buyerLen < buyers.length) {
            for (int i = 0; i < buyers.length; i++) {
                if (buyers[i] == null) {
                    buyers[i] = new Buyer(name, password, address);
                    buyerLen++;
                    break;
                }
            }
        } else {
            buyers = MakeNewBuyersArr(buyers, buyers.length, name, password, address);
        }
        Buyer current = new Buyer(name, password, address);
        DataBaseManager.insertBuyer(current);
    }

    private static void validPassword(String password) throws WrongInput {
        if (password.isEmpty() || password.contains(" ")) {
            throw new WrongInput("Password cannot contain spaces or be empty ");
        }
    }

    public static void AddProductToSeller(Scanner s1) {
        Product p1 = null;
        int numofseller;

        if (sellerLen == 0) {
            System.out.println("there are no sellers");
        } else {
            DisplaySellers();
            System.out.print("Choose Sellers Number: ");

            while (true) {
                try {
                    userInput = s1.nextLine();
                    numofseller = changeToInt(userInput);
                    break;
                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }

            }
            while (sellers.length < numofseller || numofseller < 1 || sellers[numofseller - 1] == null) {
                System.out.println("Seller doesn't exist, enter a new number : ");
                while (true) {
                    try {
                        userInput = s1.nextLine();
                        numofseller = changeToInt(userInput);
                        break;
                    } catch (WrongInput e) {
                        System.out.println(e.getMessage());
                    }

                }
            }
            System.out.println("Enter products Name :");
            String productName;
            while (true) {
                try {
                    productName = s1.next();
                    validProductName(productName);
                    break;

                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Enter Product Category :");
            DislplayCategories();
            String productCategory = s1.nextLine();
            productCategory = productCategory.toUpperCase();

            while (!categoryExists(productCategory)) {
                System.out.println("The Category Dose not Exist please Enter a new one : ");
                productCategory = s1.nextLine();
                productCategory = productCategory.toUpperCase();

            }
            float price;
            System.out.println("Enter products Price :");
            while (true) {
                try {
                    userInput = s1.nextLine();
                    price = changeToFloat(userInput);
                    break;
                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }

            }
            p1 = new Product(productName, price, Category.valueOf(productCategory));
            System.out.println("Does the product require special packaging : (y/n) ");
            String answer = s1.nextLine();
            answer = answer.toUpperCase();
            while (!answer.equals("Y") && !answer.equals("N")) {
                System.out.println("invalid answer , please enter y/n : ");
                answer = s1.nextLine();
                answer = answer.toUpperCase();
            }
            if (answer.equals("Y")) {
                System.out.print("enter how much does the extra packaging costs : ");
                Float addedValue;
                while (true) {
                    try {
                        userInput = s1.nextLine();
                        addedValue = changeToFloat(userInput);
                        break;
                    } catch (WrongInput e) {
                        System.out.println(e.getMessage());
                    }
                }
                p1.setExtraPackaging(addedValue);
            }
            sellers[numofseller - 1].addProduct(p1);
            DataBaseManager.insertProductToSeller(p1, sellers[numofseller - 1]);
        }
    }

    public static void validProductName(String productName) throws WrongInput {
        if (productName.isEmpty()) {
            throw new WrongInput("Product Name cannot be empty ");
        }
    }


    public static float changeToFloat(String userInput) throws WrongInput {
        try {
            return Float.parseFloat(userInput);
        } catch (NumberFormatException e) {
            throw new WrongInput("Wrong input, enter a valid price");
        }
    }


    public static void AddProductToBuyer(Scanner s1) {
        int numofbuyer = 0;
        int numofseller = 0;
        int productNumber = 0;


        if (buyerLen == 0) {
            System.out.println("there are no buyers");
        } else {
            DisplayBuyersCarts();
            System.out.println("choose Buyers Number : ");

            while (true) {
                try {
                    userInput = s1.nextLine();
                    numofbuyer = changeToInt(userInput);
                    break;
                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }

            }
            while (buyers.length < numofbuyer || numofbuyer < 1 || buyers[numofbuyer - 1] == null) {
                System.out.println("Buyer doesn't exist, enter a new number : ");
                while (true) {
                    try {
                        userInput = s1.nextLine();
                        numofbuyer = changeToInt(userInput);
                        break;
                    } catch (WrongInput e) {
                        System.out.println(e.getMessage());
                    }

                }
            }
            DisplaySellers();
            System.out.print("choose Sellers Number you want to buy from: ");
            while (true) {
                try {
                    userInput = s1.nextLine();
                    numofseller = changeToInt(userInput);
                    break;
                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }

            }
            while (sellers.length < numofseller || numofseller < 1) {
                System.out.println("Seller doesn't exist, enter a new number : ");
                while (true) {
                    try {
                        userInput = s1.nextLine();
                        numofseller = changeToInt(userInput);
                        break;
                    } catch (WrongInput e) {
                        System.out.println(e.getMessage());
                    }

                }
            }
            System.out.println("seller name : " + sellers[numofseller - 1].getUserName());
            sellers[numofseller - 1].printProducts();
            System.out.println("choose product number: ");
            while (true) {
                try {
                    userInput = s1.nextLine();
                    productNumber = changeToInt(userInput);
                    break;
                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }

            }
            while (sellers[numofseller - 1].getProducts().length < productNumber || productNumber < 1) {
                System.out.println("Product doesn't exist, enter a new number : ");
                productNumber = s1.nextInt();
            }

        }
        if (buyerLen != 0) {
            DataBaseManager.addProductToBuyersCart(buyers[numofbuyer - 1], sellers[numofseller - 1].getProduct(productNumber - 1));
            buyers[numofbuyer - 1].addToCart(sellers[numofseller - 1].getProduct(productNumber - 1));
        }


    }

    public static void PayForBuyersCart(Scanner s1) {
        DisplayBuyersCarts();
        System.out.println("Choose Buyers Number to pay for his cart :");
        int numofbuyer;
        while (true) {
            try {
                userInput = s1.nextLine();
                numofbuyer = changeToInt(userInput);
                break;
            } catch (WrongInput e) {
                System.out.println(e.getMessage());
            }

        }
        while (buyers.length < numofbuyer || numofbuyer < 1 || buyers[numofbuyer - 1] == null) {
            System.out.println("Buyer doesn't exist, enter a new number : ");
            while (true) {
                try {
                    userInput = s1.nextLine();
                    numofbuyer = changeToInt(userInput);
                    break;
                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }

            }
        }
        if (buyers[numofbuyer - 1].getCart() == null) {
            System.out.println("the cart is empty!");
        } else {
            System.out.println("Total amount to pay : " + buyers[numofbuyer - 1].getCartTotal());
            System.out.println("Payed successfully");
            currentDate = LocalDate.now();
            buyers[numofbuyer - 1].getCart().setCartDate(currentDate);
            System.out.println("Cart moved to history, Date : " + currentDate);
            DataBaseManager.addCartToHistory(buyers[numofbuyer - 1]);
            DataBaseManager.deleteBuyerCart(buyers[numofbuyer - 1]);
            if (ordersHistory == null) {
                ordersHistory = new OrdersHistory(buyers[numofbuyer - 1].getCart(), numofbuyer - 1, buyers.length);
            } else {
                ordersHistory.addCartToHistory(buyers[numofbuyer - 1].getCart(), numofbuyer - 1, buyers.length);
            }
            buyers[numofbuyer - 1].resetCart();
        }
    }

    public static void DisplayBuyers() {
        Buyer[] buyers1 = new Buyer[buyerLen];
        for (int i = 0; i < buyerLen; i++) {
            buyers1[i] = buyers[i];
        }
        Arrays.sort(buyers1, Comparator.comparing(Buyer::getUserName));
        System.out.println("Buyers list :");
        for (int i = 0; i < buyerLen; i++) {
            System.out.println(i + 1 + "-  " + buyers1[i]);
            System.out.println("Buyer Previous carts : ");
            if (ordersHistory != null) {
                if (ordersHistory.getBuyersPreviousCarts(i) != null) {
                    System.out.println(ordersHistory.printBuyersPreviousCarts(i));
                } else {
                    System.out.println("There are no previous carts");
                }
            } else {
                System.out.println("There are no previous carts");
            }
            System.out.println("___________________________________________________");
        }

    }

    public static void DisplaySellers() {
        Seller[] sellers1 = new Seller[sellerLen];
        for (int i = 0; i < sellerLen; i++) {
            sellers1[i] = sellers[i];
        }
        Arrays.sort(sellers1, Comparator.comparingInt(Seller::getProductslen).reversed());
        System.out.println("Sellers List : ");
        for (int i = 0; i < sellerLen; i++) {
            if (sellers1[i] != null)
                System.out.println((i + 1) + "_" + sellers1[i]);

        }
    }

    public static void DisplayBuyersCarts() {
        System.out.println("Buyers list :");
        for (int i = 0; i < buyers.length; i++) {
            if (buyers[i] != null) {
                System.out.println(i + 1 + "-  " + buyers[i]);
            }
        }
    }

    public static Seller[] MakeNewSellersArr(Seller[] l1, int len, String name, String password) {
        Seller[] l2 = new Seller[sellerLen * 2];
        for (int i = 0; i < len; i++) {
            l2[i] = l1[i];
        }
        l2[len] = new Seller(name, password);
        sellerLen++;
        return l2;
    }

    public static boolean SellerExists(Seller[] l1, String name) {
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != null) {
                if (l1[i].getUserName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Buyer[] MakeNewBuyersArr(Buyer[] l1, int len, String name, String password, Address address) {
        Buyer[] l2 = new Buyer[buyerLen * 2];
        for (int i = 0; i < len; i++) {
            l2[i] = l1[i];
        }
        l2[len] = new Buyer(name, password, address);
        buyerLen++;
        return l2;
    }

    public static boolean BuyerExists(Buyer[] l1, String name) {
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != null) {
                if (l1[i].getUserName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean categoryExists(String productCategory) {
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(productCategory)) {
                return true;
            }
        }
        return false;
    }

    public static void DislplayCategories() {
        int i = 1;
        for (Category category : Category.values()) {
            System.out.println(i + " - " + category);
            i++;
        }

    }

    public static void displayProductsFromCategory(Scanner s1) {
        DislplayCategories();
        System.out.println("choose category : ");
        String c = s1.nextLine();
        while (!categoryExists(c.toUpperCase())) {
            System.out.println("The Category Dose not Exist please Enter a new one : ");
            c = s1.nextLine();
        }
        c = c.toUpperCase();
        System.out.println(c);
        int count = 1;
        System.out.println("products in " + c + " category");
        for (int i = 0; i < sellers.length; i++) {
            for (int j = 0; j < sellers[i].getProductslen(); j++) {
                if (sellers[i].getProduct(j).getCategoryName().equals(c)) {
                    System.out.println("product " + count + ")" + sellers[i].getProduct(j));
                    count++;
                }
            }
        }
    }

    public static void chooseAnOldCartToAdd(Scanner s1) {
        int numofbuyer = -1;
        if (buyers == null || buyerLen == 0) {
            System.out.println("there are no buyers");
        } else {
            DisplayBuyers();
            System.out.println("choose the buyers number that you want his cart to be replaced : ");
            while (true) {
                try {
                    userInput = s1.nextLine();
                    numofbuyer = changeToInt(userInput);
                    break;
                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }

            }
            while (buyers.length < numofbuyer || numofbuyer < 1 || buyers[numofbuyer - 1] == null) {
                System.out.println("Buyer doesn't exist, enter a new number : ");
                while (true) {
                    try {
                        userInput = s1.nextLine();
                        numofbuyer = changeToInt(userInput);
                        break;
                    } catch (WrongInput e) {
                        System.out.println(e.getMessage());
                    }

                }
            }

            if (numofbuyer != -1) {
                int count = 1;
                for (int j = 0; j < ordersHistory.getCarts()[numofbuyer - 1].length; j++) {
                    if (ordersHistory.getCarts()[numofbuyer - 1][j] != null) {
                        System.out.println("cart " + count + ") " + ordersHistory.getCarts()[numofbuyer - 1][j]);
                    }
                }
                if (buyers[numofbuyer - 1].getCart() != null) {
                    System.out.println("do you want to replace your current cart : (Y/N)");
                    String answer = s1.nextLine();
                    answer = answer.toUpperCase();
                    while (!answer.equals("Y") && !answer.equals("N")) {
                        System.out.println("invalid answer , please enter Y/N : ");
                        answer = s1.nextLine();
                        answer = answer.toUpperCase();
                    }
                    if (answer.equals("Y")) {
                        System.out.println("choose the cart number that you want to replace with your current cart : ");
                        int n;
                        while (true) {
                            try {
                                userInput = s1.nextLine();
                                n = changeToInt(userInput);
                                break;
                            } catch (WrongInput e) {
                                System.out.println(e.getMessage());
                            }

                        }
                        while ((n - 1) < 0 || n > ordersHistory.getCarts()[numofbuyer - 1].length) {
                            System.out.print("cart does not exist, enter a new number please : ");
                            while (true) {
                                try {
                                    userInput = s1.nextLine();
                                    n = changeToInt(userInput);
                                    break;
                                } catch (WrongInput e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                        }

                    }
                } else {
                    System.out.println("choose the cart number that you want to set as your cart : ");
                    int n;
                    while (true) {
                        try {
                            userInput = s1.nextLine();
                            n = changeToInt(userInput);
                            break;
                        } catch (WrongInput e) {
                            System.out.println(e.getMessage());
                        }

                    }
                    while ((n - 1) < 0 || n > ordersHistory.getCarts()[numofbuyer - 1].length) {
                        System.out.print("cart does not exist, enter a new number please : ");
                        while (true) {
                            try {
                                userInput = s1.nextLine();
                                n = changeToInt(userInput);
                                break;
                            } catch (WrongInput e) {
                                System.out.println(e.getMessage());
                            }

                        }
                    }
                    Cart c1 = ordersHistory.getCarts()[numofbuyer - 1][n - 1];
                    buyers[numofbuyer - 1].setCart(c1);
                }
            }
        }

    }


    public static OrdersHistory getOrdersHistory() {
        return ordersHistory;
    }

    public static void setOrdersHistory(OrdersHistory ordersHistory) {
        Admin.ordersHistory = ordersHistory;
    }

    public static Seller[] getSellers() {
        return sellers;
    }

    public static void setSellers(Seller[] sellers) {
        Admin.sellers = sellers;
    }

    public static Buyer[] getBuyers() {
        return buyers;
    }

    public static void setBuyers(Buyer[] buyers) {
        Admin.buyers = buyers;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Admin.name = name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Admin.password = password;
    }

    public static String getBuildingNumber() {
        return BuildingNumber;
    }

    public static void setBuildingNumber(String buildingNumber) {
        BuildingNumber = buildingNumber;
    }

    public static String getCityName() {
        return CityName;
    }

    public static void setCityName(String cityName) {
        CityName = cityName;
    }

    public static String getCountryName() {
        return CountryName;
    }

    public static void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public static String getStreetName() {
        return StreetName;
    }

    public static void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public static void loadAllData() {
        loadSellers();
        loadBuyers();
        loadSellersProducts();
    }

    public static void loadSellers() {
        String countSql = "SELECT COUNT(*) FROM Seller";
        String fetchSql = """
        SELECT s.userName, a.password
        FROM Seller s
        JOIN Accounts a ON s.userName = a.userName
        ORDER BY s.sellerID
    """;

        try (Connection conn = DriverManager.getConnection(DataBaseManager.getUrl(),
                DataBaseManager.getUser(),
                DataBaseManager.getPassword());
             Statement countStmt = conn.createStatement();
             ResultSet countRs = countStmt.executeQuery(countSql)) {

            if (countRs.next()) {
                sellerLen = countRs.getInt(1);
                sellers = new Seller[sellerLen];
            }

            try (Statement fetchStmt = conn.createStatement();
                 ResultSet rs = fetchStmt.executeQuery(fetchSql)) {

                int i = 0;
                while (rs.next() && i < sellerLen) {
                    sellers[i++] = new Seller(
                            rs.getString("userName"),
                            rs.getString("password")
                    );
                }

                System.out.println("Loaded " + i + " sellers.");
            }

        } catch (SQLException e) {
            System.err.println("Error loading sellers:");
            e.printStackTrace();
        }
    }



    public static void loadBuyers() {
        String countSql = "SELECT COUNT(*) FROM Buyer";
        String fetchSql = """
        SELECT b.userName, a.password, b.address
        FROM Buyer b
        JOIN Accounts a ON b.userName = a.userName
        ORDER BY b.buyerID
    """;

        try (Connection conn = DriverManager.getConnection(DataBaseManager.getUrl(),
                DataBaseManager.getUser(),
                DataBaseManager.getPassword());
             Statement countStmt = conn.createStatement();
             ResultSet countRs = countStmt.executeQuery(countSql)) {

            if (countRs.next()) {
                buyerLen = countRs.getInt(1);
                buyers = new Buyer[buyerLen];
            }

            try (Statement fetchStmt = conn.createStatement();
                 ResultSet rs = fetchStmt.executeQuery(fetchSql)) {

                int i = 0;
                while (rs.next() && i < buyerLen) {
                    buyers[i++] = new Buyer(
                            rs.getString("userName"),
                            rs.getString("password"),
                            rs.getString("address")
                    );
                }

                System.out.println("Loaded " + i + " buyers.");
            }

        } catch (SQLException e) {
            System.err.println("Error loading buyers:");
            e.printStackTrace();
        }
    }


    private static int findSellerIndex(String sellerName) {
        for (int i = 0; i < sellers.length; i++) {
            Seller s = sellers[i];
            if (s != null && s.getUserName().equals(sellerName)) {
                return i;
            }
        }
        return -1;
    }

    public static void loadSellersProducts() {
        String sql = """
        SELECT productName, price, serialNumber, category, sellerUserName
        FROM Products
    """;

        try (Connection conn = DriverManager.getConnection(DataBaseManager.getUrl(),
                DataBaseManager.getUser(),
                DataBaseManager.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int added = 0, skipped = 0;

            while (rs.next()) {
                String sellerName = rs.getString("sellerUserName");

                Product p = new Product(
                        rs.getString("productName"),
                        rs.getFloat("price"),
                        rs.getString("category"),
                        rs.getString("serialNumber")
                );

                boolean found = false;
                for (Seller s : sellers) {
                    if (s != null && s.getUserName().equals(sellerName)) {
                        s.addProduct(p);
                        added++;
                        found = true;
                        break;
                    }
                }

                if (!found) skipped++;
            }

            System.out.printf("Loaded %d products into sellers (%d skipped).%n", added, skipped);

        } catch (SQLException e) {
            System.err.println("Error loading products:");
            e.printStackTrace();
        }
    }
}