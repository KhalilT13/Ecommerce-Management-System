package ECommerceDBP;


import java.math.BigDecimal;

public class Buyer implements Comparable<Buyer>, IBuyer {
    private String userName;
    private String password;
    private Address address;
    private Cart cart;
    private int cartlen = 0;

    public Buyer(String userName, String password, Address address) {
        this.userName = userName;
        this.password = password;
        this.address = address;
    }
    public Buyer(String userName, String password,String address) {

        this.userName = userName;
        this.password = password;

        // 2. split on commas, ignore surrounding blanks – limit=4 stops after 3rd comma
        String[] parts = address.trim().split("\\s*,\\s*", 4);

        // 3. validate we really got 4 items
        if (parts.length != 4) {
            throw new IllegalArgumentException(
                    "Address must have exactly 4 comma-separated parts: "
                            + "country, city, street, building number - got '" + address + "'");
        }

        // 4. build the immutable Address value object (all fields already trimmed)
        this.address = new Address(
                parts[0],  // country
                parts[1],  // city
                parts[2],  // street
                parts[3]   // buildingNumber (kept as String)
        );

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address.toString();
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCart(Cart c1) {
        this.cart = c1;
        this.cartlen = c1.getProducts().length;
    }

    public Cart getCart() {
        return cart;
    }

    public void addToCart(Product p1) {
        if (cartlen == 0) {
            cart = new Cart(p1);
            cartlen++;
        } else {
            cart.addProductToCart(p1, cartlen);
            cartlen++;
        }
    }

    public float getCartTotal() {
        if (this.cart == null){
            return 0;
        }
        return cart.getCartSum();
    }
    public BigDecimal getBigDecemalCartTotal() {
        BigDecimal tempPrice = BigDecimal.valueOf((float)this.getCartTotal());
        return tempPrice;

    }

    public void resetCart() {
        this.cart = null;
        this.cartlen = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.userName).append(", Address: ").append(this.address).append(", cart:");
        if (cartlen == 0) {
            sb.append("empty");
            return sb.toString();
        } else {
            sb.append("\n").append(this.cart).append("\n");
        }
        return sb.toString();
    }


    @Override
    public int compareTo(Buyer other) {
        return this.userName.compareTo(other.userName);
    }

    public boolean productExists(Product product){
        if (cart == null || cart.getProducts() == null) {
            return false;
        }

        for (Product p : cart.getProducts()) {
            if (p != null && p.getProductName().equals(product.getProductName())) {
                return true;
            }
        }
        return false;
    }
}