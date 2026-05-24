package ECommerceDBP;


import java.math.BigDecimal;
import java.util.Random;

public class Product {
    private String productName;
    private float price;
    private Category category;
    private String serialNumber;
    private float extraPackaging = 0;

    public Product() {
        productName = "";
        price = 0;
    }

    public Product(String productName, float price, Category category, String serialNumber, float extraPackaging) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.serialNumber = serialNumber;
        this.extraPackaging = extraPackaging;

    }

    public Product(String productName, float price, String category, String serialNumber) {
        this.productName = productName;
        this.price = price;
        switch (category) {
            case "Kids" -> this.category = Category.KIDS;
            case "Electronics" -> this.category = Category.ELECTRONICS;
            case "Office" -> this.category = Category.OFFICE;
            case "Clothes" -> this.category = Category.CLOTHES;
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        }
        this.serialNumber = serialNumber;
        this.extraPackaging = 0;

    }

    public Product(String productName, float price, Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.serialNumber = setRandomSerialNumber();
    }

    private String setRandomSerialNumber() {
        String serialNum = "";
        Random random = new Random();
        for (int i = 0; i < 13; i++) {
            serialNum += random.nextInt(10);
        }
        return serialNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public BigDecimal getDecimalPrice() {
        BigDecimal tempPrice = BigDecimal.valueOf((float) price);
        return tempPrice;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String getProductCategory() {
        switch (this.category) {
            case KIDS:
                return "Kids";
            case ELECTRONICS:
                return "Electronics";
            case OFFICE:
                return "Office";
            case CLOTHES:
                return "Clothes";
            default:
                return null;
        }
    }

    public String getCategoryName() {
        if (category != null)
            return category.name();
        return "";
    }

    public void setExtraPackaging(float e) {
        extraPackaging = e;
        price += e;
    }


    @Override
    public String toString() {
        if (extraPackaging == 0) {
            return " " + productName + ", price : $" + price + ", Category : " + category.name() + ", serialNumber : " + serialNumber;
        } else {
            return " " + productName + ", price : $" + (price - extraPackaging) + ", extraPackaging price : $" + extraPackaging +
                    ", total price: $" + price + ", Category : " + category.name() + ", serialNumber : " + serialNumber;
        }
    }


    public BigDecimal getExtraPackaging() {
        BigDecimal tempPrice = BigDecimal.valueOf((float) extraPackaging);
        return tempPrice;
    }
}
