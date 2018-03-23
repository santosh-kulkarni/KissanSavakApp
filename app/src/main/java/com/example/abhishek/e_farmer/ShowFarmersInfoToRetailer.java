package com.example.abhishek.e_farmer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowFarmersInfoToRetailer extends AppCompatActivity {

    static ArrayList<String> checkedList;
    RecyclerView myrv;
    List<FarmerProductDetails> lstFarmerDetails = new ArrayList<FarmerProductDetails>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_farmers_info_to_retailer);
        //lstFarmerDetails.add(new FarmerProductDetails("sdscsc2sw_ig9","Carrot","23deasas","Farmer1","40","600kg"));


        myrv = findViewById(R.id.recycleRetailer);
        Intent intent =  getIntent();
        checkedList = intent.getStringArrayListExtra("CheckList");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("EFarmer/PDetails");
        databaseReference.addListenerForSingleValueEvent(showListToRetailer);
    }

    ValueEventListener showListToRetailer = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String name = "Farmer";
            int count = 1;
            for(DataSnapshot formerKey : dataSnapshot.getChildren()) {
                for(DataSnapshot productKey : formerKey.getChildren()) {
                    ProductDetails productDetails = productKey.getValue(ProductDetails.class);
                    if(checkedList.contains(productDetails.ProductCategory)) {
                        lstFarmerDetails.add(new FarmerProductDetails(productKey.getKey(),productDetails.ProductName,productDetails.ProductCategory,formerKey.getKey(),name + count ,productDetails.ProductBasePrice,productDetails.ProdQuantity));
                        FarmerDetailsAdapter myAdapter = new FarmerDetailsAdapter(ShowFarmersInfoToRetailer.this, lstFarmerDetails);
                        myrv.setLayoutManager(new GridLayoutManager(ShowFarmersInfoToRetailer.this,1));
                        myrv.setAdapter(myAdapter);

                        //Log.i("Value1",productDetails.ProdQuantity + productDetails.ProductCategory + productDetails.ProductBasePrice + productDetails.ProductName);
                    }
                }
                count++;
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



}
