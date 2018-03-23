package com.example.abhishek.e_farmer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BiddingActivity extends AppCompatActivity {

    String name, quantity, price, productID, formerID, retailerID;
    EditText biddingPrice;
    TextView textName, textQuantity, textPrice;
    ArrayAdapter<String> productDetails;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidding);
        getSupportActionBar().hide();

        final Intent intent = getIntent();
        final ArrayList<String> productDetails = intent.getStringArrayListExtra("ProductDetails");

        name = productDetails.get(2);
        productID = productDetails.get(1);
        quantity = productDetails.get(5);
        formerID = productDetails.get(4);
        price = productDetails.get(6);

        retailerID = FindTheKey.FRID;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Start Bidding");

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.bidding, null);

        textName = view.findViewById(R.id.text1);
        textQuantity = view.findViewById(R.id.text2);
        textPrice = view.findViewById(R.id.text3);
        biddingPrice = view.findViewById(R.id.edittextView);

        textName.setText(name);
        textQuantity.setText(quantity);
        textPrice.setText(price);

        builder.setView(view);

        builder.setPositiveButton("START", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("Value","BiddingTransaction/".concat(formerID).concat("/").concat(productID).replaceAll(" ",""));
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("EFarmer/BiddingTransaction/" +formerID+ "/"+productID);
                String string = databaseReference.push().getKey();
                Log.i("Retailer Id",string);
                if(Integer.parseInt(biddingPrice.getText().toString()) < Integer.parseInt(price)) {
                    Toast.makeText(BiddingActivity.this,"Please Enter a Value Greater than the price",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(BiddingActivity.this,BiddingActivity.class);
                    intent1.putExtra("ProductDetails",productDetails);
                    startActivity(intent1);
                }
                TransactionDetails transactionDetails = new TransactionDetails(retailerID, biddingPrice.getText().toString());
                databaseReference.child(string).setValue(transactionDetails);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent1 = new Intent(getApplicationContext(), RetailerActivity.class);
                startActivity(intent1);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
