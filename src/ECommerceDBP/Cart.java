package ECommerceDBP;


import java.time.LocalDate;

public class Cart {
    private Product[] products;
    private float cartSum = 0;
    private LocalDate cartDate;

    public Cart() {
        this.products = new Product[0];
    }

    public Cart(Product p1) {
        products = new Product[1];
        products[0] = p1;
        cartSum += products[0].getPrice();
    }

    public void addProductToCart(Product p1, int cartlen) {
        products = makeCopyArr(products, cartlen);
        products[cartlen] = p1;
        cartSum += products[cartlen].getPrice();
    }

    public Product[] makeCopyArr(Product[] l1, int len) {
        Product[] p1 = new Product[len + 1];
        for (int i = 0; i < l1.length; i++) {
            p1[i] = l1[i];
        }
        return p1;
    }

    public float getCartSum() {
        return cartSum;
    }

    public LocalDate getCartDate() {
        return cartDate;
    }

    public void setCartDate(LocalDate cartDate) {
        this.cartDate = cartDate;
    }


    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    @Override
    public String toString() {
        StringBuilder s1 = new StringBuilder();
        for (int i = 0; i < products.length; i++) {
            if (i == products.length - 1) {
                s1.append("product " + (i + 1) + ":" + products[i]).append(".");
            } else {
                if (i == 1) {
                    s1.append("product " + (i + 1) + ":" + products[i]).append(",");
                } else {
                    s1.append("product " + (i + 1) + ":" + products[i]).append(",");
                }
            }
        }
        return s1.toString();

    }
}
