package ECommerceDBP;

public interface ISeller {
    void addProduct(Product p1);

    Product[] makeCopyArr(Product[] l1, int len);

    void setUserName(String userName);

    void setPassword(String password);

    String getUserName();

    String getPassword();

    void printProducts();

    Product[] getProducts();

    void setProducts(Product[] products);

    int getProductslen();

    void setProductslen(int productslen);

    Product getProduct(int n);

    String toString();

    int compareTo(Seller other);

}