package ECommerceDBP;

public interface IBuyer {
    public void addToCart(Product p1);

    public void resetCart();

    public float getCartTotal();
}

