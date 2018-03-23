package com.example.abhishek.e_farmer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    Intent intent;

    public void checkUserStatus() {
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            intent = new Intent(ListActivity.this,LogInActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Log.i("Current User",FirebaseAuth.getInstance().getCurrentUser().getEmail());
        checkUserStatus();


        final ListView listView = findViewById(R.id.listview);
        ArrayList<String> arrayList = new ArrayList<String>();
        final ProgressBar progressBar = findViewById(R.id.pbar);
        arrayList.add("My Current List Of Product");
        arrayList.add("Edit My Profile");
        arrayList.add("Sing Out");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        Toast.makeText(getApplicationContext(),"Please Wait Till its get loaded",Toast.LENGTH_SHORT).show();
        listView.setEnabled(false);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, 7000)
        ;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if ( i == 0) {
                    intent = new Intent(getApplicationContext(),FarmerSaleActivity.class);
                }
                else if( i==1 ) {
                    intent = new Intent(getApplicationContext(),EditProfileActivity.class);
                }
                else if(i == 2) {
                    FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
                    Log.i("Current User",String.valueOf(firebaseAuth));
                    if(firebaseAuth != null) {
                        FirebaseAuth.getInstance().signOut();
                        intent = new Intent(getApplicationContext(), LogInActivity.class);
                    }
                }
                startActivity(intent);
            }
        });
    }
}
