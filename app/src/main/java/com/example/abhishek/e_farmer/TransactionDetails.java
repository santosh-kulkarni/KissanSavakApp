package com.example.abhishek.e_farmer;

/**
 * Created by abhishek on 20/03/18.
 */

public class TransactionDetails {
    public String RetailerID,BiddingPrice;
    public TransactionDetails() {

    }

    public TransactionDetails(String retailerID, String biddingPrice) {
        this.RetailerID = retailerID;
        this.BiddingPrice = biddingPrice;
    }
}
