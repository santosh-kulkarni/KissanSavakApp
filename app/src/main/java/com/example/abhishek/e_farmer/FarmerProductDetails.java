package com.example.abhishek.e_farmer;

/**
 * Created by abhishek on 19/03/18.
 */

public class FarmerProductDetails {
    public String productId,pName,farmerId,farmerName,bPrice,qty,pcategory;

    FarmerProductDetails(String pID,String pname,String pcategory, String fID,String fname,String bprice,String qty){
        this.productId = pID;
        this.pName = pname;
        this.farmerId = fID;
        this.farmerName = fname;
        this.bPrice = bprice;
        this.qty = qty;
        this.pcategory = pcategory;
    }

    public String getProductId() {
        return productId;
    }

    public String getPName() {
        return pName;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public String getBPrice() {
        return bPrice;
    }

    public String getQty() {
        return qty;
    }

    public String getPcategory() {
        return pcategory;
    }
}

