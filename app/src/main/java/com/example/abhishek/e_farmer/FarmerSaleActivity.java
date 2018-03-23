package com.example.abhishek.e_farmer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FarmerSaleActivity extends AppCompatActivity {

    List<Details> lstDetails;
    View view1;
    String category,product_name,product_quantity,product_price;
    //String pid="";
    String fId="",pId="";



    public void getKey() {
        DatabaseReference root1 = FirebaseDatabase.getInstance().getReference("EFarmer");

        root1.addListenerForSingleValueEvent(get_id_key);
    }
    ValueEventListener get_id_key = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot dataSnapshot1 : dataSnapshot.child(LogInActivity.type).getChildren() ) {
                User user = dataSnapshot1.getValue(User.class);
                Log.i("Fid","Hello");
                if(user.emailId.equals(LogInActivity.ID)) {
                    fId = dataSnapshot1.getKey();
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void addProductDetails() {
        String s = "EFarmer/PDetails/"+fId;
        Log.i("S = ",s);
        DatabaseReference root2 = FirebaseDatabase.getInstance().getReference(s);
        String productID = root2.push().getKey();
        pId = productID;
        Log.i("Name :",product_name);
        Log.i("Quantity :",product_quantity);
        Log.i("Base Price :",product_price);
        Log.i("Spinner :",category);
        ProductDetails product = new ProductDetails(category,product_name,product_quantity,product_price);
        root2.child(productID).setValue(product);
        Log.i("Status","Success stored in database");
    }


    public void FromDatabaseToCardView() {
        String s = "EFarmer/PDetails/" + FindTheKey.FRID;
        Log.i("Card View = ",s);
        DatabaseReference root3 = FirebaseDatabase.getInstance().getReference(s);
        root3.addListenerForSingleValueEvent(card_view_fill);

    }
    ValueEventListener card_view_fill = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            lstDetails.clear();
            RecyclerView myrv = findViewById(R.id.recycle);
            Log.i("Static Key ",dataSnapshot.getKey());
            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                ProductDetails productDetails = dataSnapshot1.getValue(ProductDetails.class);
                if(productDetails.ProductCategory.equals("Vegetables")){
                    lstDetails.add(new Details(dataSnapshot1.getKey(),productDetails.ProductCategory,
                            productDetails.ProductName,productDetails.ProdQuantity,
                            productDetails.ProductBasePrice,R.drawable.vegetable));
                    DetailsAdapter myAdapter = new DetailsAdapter(FarmerSaleActivity.this, lstDetails);
                    myrv.setLayoutManager(new GridLayoutManager(FarmerSaleActivity.this,1));
                    myrv.setAdapter(myAdapter);
                }
                else if(productDetails.ProductCategory.equals("Fruits")){
                    lstDetails.add(new Details(dataSnapshot1.getKey(),productDetails.ProductCategory,
                            productDetails.ProductName,productDetails.ProdQuantity,
                            productDetails.ProductBasePrice,R.drawable.fruits));
                    DetailsAdapter myAdapter = new DetailsAdapter(FarmerSaleActivity.this, lstDetails);
                    myrv.setLayoutManager(new GridLayoutManager(FarmerSaleActivity.this,1));
                    myrv.setAdapter(myAdapter);
                }
                else {
                    lstDetails.add(new Details(dataSnapshot1.getKey(),productDetails.ProductCategory,
                            productDetails.ProductName,productDetails.ProdQuantity,
                            productDetails.ProductBasePrice,R.drawable.flower));
                    DetailsAdapter myAdapter = new DetailsAdapter(FarmerSaleActivity.this, lstDetails);
                    myrv.setLayoutManager(new GridLayoutManager(FarmerSaleActivity.this,1));
                    myrv.setAdapter(myAdapter);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_sale);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getKey();
        lstDetails = new ArrayList<Details>() ;
        FromDatabaseToCardView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FarmerSaleActivity.this);
                builder.setTitle("Add product");

                LayoutInflater layout = getLayoutInflater();
                view1 = layout.inflate(R.layout.product_addition_info,null);

                builder.setView(view1);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText p_name = view1.findViewById(R.id.product_name);
                        product_name = p_name.getText().toString();

                        Spinner spinner = view1.findViewById(R.id.spinner);
                        category = spinner.getSelectedItem().toString();

                        Spinner spinner1 = view1.findViewById(R.id.spinner1);
                        String str1 = spinner1.getSelectedItem().toString();

                        Spinner spinner2 = view1.findViewById(R.id.spinner2);
                        String str2 = spinner2.getSelectedItem().toString();


                        EditText quantity =  view1.findViewById(R.id.quantity);
                        product_quantity = quantity.getText().toString().concat(str1);

                        EditText p_base = view1.findViewById(R.id.baseprice);
                        product_price = p_base.getText().toString().concat(str2);

                        addProductDetails();
                        FromDatabaseToCardView();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater my_menu = getMenuInflater();
        my_menu.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.settings :
                                Toast.makeText(this, "Settings Menu", Toast.LENGTH_SHORT).show();
                                return true;
            case R.id.signout :
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                if(firebaseUser != null) {
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                                    startActivity(intent);
                                }
                                return true;
        }
        return false;
    }
}
