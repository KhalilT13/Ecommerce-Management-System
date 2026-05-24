package ECommerceDBP;

public class OrdersHistory {
    private int buyersLen;
    private Cart[][] carts;
    private int cartslen;

    public OrdersHistory() {
        this.carts = null;
        this.buyersLen = 0;
        this.cartslen = 0;

    }

    public Cart[][] getCarts() {
        if (carts != null) {
            return carts;
        }else {
            return null;
        }
    }

    public OrdersHistory(Cart c1, int buyerNum, int buyersLength) {
        this.buyersLen = buyersLength;
        carts = new Cart[buyersLen][];
        carts[buyerNum] = new Cart[1];
        carts[buyerNum][0] = c1;
        cartslen ++;

    }

    public void addCartToHistory(Cart c1, int buyerNum, int buyersLen) {
        if (buyersLen == this.buyersLen) {
            carts = addNewCartToArr(carts, buyerNum, c1);
        } else {
            this.buyersLen += 1;
            carts = addNewBuyersCartToArr(carts, c1);
        }
        cartslen ++;
    }

    public Cart[][] addNewCartToArr(Cart[][] carts, int buyerNum, Cart c1) {
        Cart[][] current = new Cart[carts.length][];
        for (int i = 0; i < carts.length; i++) {
            if (i == buyerNum) {
                if (carts[i] != null) {
                    current[i] = new Cart[carts[i].length + 1];
                    for (int j = 0; j < carts[i].length; j++) {
                        current[i][j] = carts[i][j];
                    }
                    current[i][carts[i].length] = c1;
                } else {
                    current[i] = new Cart[1];
                    current[i][0] = c1;
                }
            } else {
                current[i] = carts[i];
            }
        }
        return current;
    }
    private Cart[][] addNewBuyersCartToArr(Cart[][] carts, Cart c1) {
        Cart[][] current = new Cart[carts.length * 2][];
        for (int i = 0; i < carts.length; i++) {
            current[i] = carts[i];
        }
        current[carts.length] = new Cart[1];
        current[carts.length][0] = c1;
        return current;
    }

    public Cart[] makeCopyArr(Cart[] l1, int len) {
        Cart[] p1 = new Cart[len + 1];
        for (int i = 0; i < l1.length; i++) {
            p1[i] = l1[i];
        }
        return p1;
    }

    public Cart[] getBuyersPreviousCarts(int buyersNum) {
        if (buyersNum >= this.buyersLen)
            return null;
        return carts[buyersNum];
    }

    public int getCartslen() {
        return cartslen;
    }

    public void setCartslen(int cartslen) {
        this.cartslen = cartslen;
    }

    public String printBuyersPreviousCarts(int numOfBuyer) {
        StringBuilder sb = new StringBuilder();
        if (carts[numOfBuyer].length == 0 || carts[numOfBuyer] == null) {
            return "There are no previous carts";
        } else {
            for (int i = 0; i < carts[numOfBuyer].length; i++) {
                sb.append("cart " + (i + 1) + "- Date : ").append(carts[numOfBuyer][i].getCartDate());
                sb.append("\n").append(carts[numOfBuyer][i]).append("\n");
            }
        }
        return sb.toString();
    }
}