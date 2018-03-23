package com.example.abhishek.e_farmer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ShowRetailerInFarmer extends AppCompatActivity {

    String id;
    ListView listView;
    ArrayList<String> arrayList;
    Button button1,button2;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_retailer_in_farmer);

        Intent intent = getIntent();
        String productID = intent.getStringExtra("Value");
        listView = findViewById(R.id.biddingList);
        arrayList = new ArrayList<String>();
        FindTheKey.FRID.replaceAll(" ","");

        button1 = findViewById(R.id.acceptButton);
        button2 = findViewById(R.id.rejectButton);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowRetailerInFarmer.this);
                builder.setMessage("Transaction Is Successful");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent1 = new Intent(getApplicationContext(),ListActivity.class);
                        startActivity(intent1);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        String transactionPath = "EFarmer/BiddingTransaction/ "+FindTheKey.FRID+"/ "+productID;
        Log.i("Value",transactionPath);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(transactionPath);
        databaseReference.addListenerForSingleValueEvent(BiddingList);
    }
    ValueEventListener BiddingList = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                Log.i("Status",dataSnapshot1.getKey());
                TransactionDetails transactionDetails = dataSnapshot1.getValue(TransactionDetails.class);
                arrayList.add(transactionDetails.BiddingPrice);
            }
            for(String arr : arrayList)
                Log.i("arr",arr);
            Collections.sort(arrayList);
            Collections.reverse(arrayList);
            arrayAdapter = new ArrayAdapter<String>(ShowRetailerInFarmer.this,android.R.layout.simple_list_item_1,arrayList);
            listView.setAdapter(arrayAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
