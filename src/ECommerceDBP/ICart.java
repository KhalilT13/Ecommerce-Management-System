package ECommerceDBP;


public interface ICart {
    public void addProductToCart(Product p1, int cartlen);
    public Product[] makeCopyArr(Product[] l1, int len);
}