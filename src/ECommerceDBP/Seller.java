package ECommerceDBP;

import java.util.Arrays;

public class Seller implements Comparable<Seller>, ISeller {
    private String userName;
    private String password;
    private Product[] products;
    private int productslen;


    public Seller(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.products = new Product[1];
        this.productslen = 0;
    }

    public void addProduct(Product p1) {
        if (productslen == 0) {
            // products = new Product[0];
            products[0] = p1;
            productslen++;
        } else {
            products = makeCopyArr(products, productslen);
            products[productslen] = p1;
            productslen++;

        }
    }

    public Product[] makeCopyArr(Product[] l1, int len) {
        Product[] p1 = new Product[productslen * 2];
        for (int i = 0; i < l1.length; i++) {
            p1[i] = l1[i];
        }
        return p1;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void printProducts() {
        for (int i = 0; i < productslen; i++) {
            System.out.println("product " + (i + 1) + ", " + products[i]);
        }
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public int getProductslen() {
        return productslen;
    }

    public void setProductslen(int productslen) {
        this.productslen = productslen;
    }

    public Product getProduct(int n) {
        return products[n];
    }

    @Override
    public String toString() {
        StringBuilder s1 = new StringBuilder();
        s1.append(this.userName + ", Products : ").append("\n");
        for (int i = 0; i < productslen; i++) {
            s1.append("           ").append((i + 1)).append(")");
            if (i == productslen - 1) {
                s1.append(products[i]).append(".");
            } else {
                s1.append(products[i]).append(", ");
            }
            s1.append("\n");
        }
        s1.append("___________________________________________________");
        return s1.toString();
    }

    @Override
    public int compareTo(Seller other) {
        return Integer.compare(this.productslen, other.getProductslen());
    }
}
