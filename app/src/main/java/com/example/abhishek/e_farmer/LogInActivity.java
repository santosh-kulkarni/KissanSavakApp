package com.example.abhishek.e_farmer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    Button logInFarmer, logInRetailer, signUp;
    EditText phoneField, passwordField;
    ProgressDialog progressDialog;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    static String ID;
    static String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        phoneField = findViewById(R.id.phone);
        passwordField = findViewById(R.id.password);

        logInFarmer = findViewById(R.id.loginFarmer);
        logInRetailer = findViewById(R.id.loginRetailer);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Sign UP");
                builder.setMessage("Create a new account as a");
                builder.setPositiveButton("Farmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), PhoneVerificationActivity.class);
                        intent.putExtra("value", "Farmer");
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Retailer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), PhoneVerificationActivity.class);
                        intent.putExtra("value", "Retailer");
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        logInFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = phoneField.getText().toString();
                String pass = passwordField.getText().toString();
                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(LogInActivity.this, "Email Id or password is wrong!", Toast.LENGTH_SHORT).show();
                    phoneField.setText("");
                    passwordField.setText("");
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this);
                    progressDialog.setMessage("Logging You In...");
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(phoneField.getText().toString(), passwordField.getText().toString())
                            .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                   /*  else {*/
                                        Toast.makeText(LogInActivity.this, "Log In Success", Toast.LENGTH_SHORT).show();
                                        Log.i("Current User: ", String.valueOf(mAuth.getCurrentUser()));
                                        ID = mAuth.getCurrentUser().getEmail();
                                        type = "Farmer";
                                        FindTheKey obj = new FindTheKey();
                                        obj.getKey();
                                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                                        startActivity(intent);
                                        phoneField.setText("");
                                        passwordField.setText("");
                                    } else {
                                        Toast.makeText(LogInActivity.this, "Email or Password is wrong", Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.cancel();
                                }
                            });
                }
            }
        });

        logInRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = phoneField.getText().toString();
                String pass = passwordField.getText().toString();
                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(LogInActivity.this, "Email Id or password is wrong!", Toast.LENGTH_SHORT).show();
                    phoneField.setText("");
                    passwordField.setText("");
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this);
                    progressDialog.setMessage("Logging You In As Retailer...");
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(phoneField.getText().toString(), passwordField.getText().toString())
                            .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LogInActivity.this, "Log In Success", Toast.LENGTH_SHORT).show();
                                        Log.i("Current User: ", String.valueOf(mAuth.getCurrentUser()));
                                        ID = mAuth.getCurrentUser().getEmail();
                                        type = "Retailer";
                                        FindTheKey obj = new FindTheKey();
                                        obj.getKey();
                                        Intent intent = new Intent(getApplicationContext(), RetailerActivity.class);
                                        startActivity(intent);
                                        phoneField.setText("");
                                        passwordField.setText("");
                                    } else {
                                        Toast.makeText(LogInActivity.this, "Email or Password is wrong", Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.cancel();
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null) {
            FirebaseAuth.getInstance().signOut();
        }
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
