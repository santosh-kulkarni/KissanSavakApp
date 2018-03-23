package com.example.abhishek.e_farmer;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.*;

import java.util.concurrent.TimeUnit;


public class SignUpActivity extends AppCompatActivity {

    String status = "false";
    EditText phoneNumber, firstName, lastName, emailId, password, confirmPassword,address;
    Button signUp;

    public String fName, lName, email, pword, confirmpword, pno, title,add;
    private DatabaseReference root;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String number = intent.getStringExtra("Number");
        title = PhoneVerificationActivity.title;
        //Log.i("Number", number);
        Log.i("Title", title);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.mobileNumber);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailId = findViewById(R.id.emailId);
        password = findViewById(R.id.signUpPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUp = findViewById(R.id.signUp);

        phoneNumber.setText(number);
        address.setText("-");
        phoneNumber.setEnabled(false);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                fName = firstName.getText().toString();
                lName = lastName.getText().toString();
                email = emailId.getText().toString();
                pno = phoneNumber.getText().toString();
                pword = password.getText().toString();
                confirmpword = confirmPassword.getText().toString();
                add = address.getText().toString();


                if (fName.equals("") || lName.equals("") || email.equals("") || pno.equals("") || pword.equals("") || confirmpword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Fill All the Inputs", Toast.LENGTH_SHORT).show();
                } else {
                    if (pword.equals(confirmpword)) {
                        signIn(email, pword);
                        root = FirebaseDatabase.getInstance().getReference("EFarmer");
                        String id = root.push().getKey();
                        Log.i("ID :", id);
                        User user = new User(fName, lName, email, pno, pword,add);
                        root.child(title).child(id).setValue(user);
                        Log.i("Status", " Dat successfully added");
                        Intent intent1 = new Intent(getApplicationContext(),LogInActivity.class);
                        startActivity(intent1);

                    } else {
                        Toast.makeText(getApplicationContext(), "password entered incorrectly..Please re-enter password", Toast.LENGTH_SHORT).show();
                        password.setText("");
                        confirmPassword.setText("");
                    }
                }
            }
        });
    }

    private void signIn(String email, String pwd) {
        fAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Status", "signin successful");

                        } else {
                            Log.w("Status ", task.getException());
                            Toast.makeText(getApplicationContext(),"Invalid Email Or week password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
