package com.example.abhishek.e_farmer;

/**
 * Created by abhishek on 17/03/18.
 */

public class Details {
    public String categoryName;
    public String productName;
    public String quantity;
    public String basePrice;
    public String pId;
    public int thumbNail;

    public Details(String pId, String categoryName, String productName, String quantity, String basePrice, int thumbNail) {
        this.pId = pId;
        this.categoryName = categoryName;
        this.productName = productName;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.thumbNail = thumbNail;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public int getThumbNail() {
        return thumbNail;
    }

    public String getpId() {
        return pId;
    }
}
