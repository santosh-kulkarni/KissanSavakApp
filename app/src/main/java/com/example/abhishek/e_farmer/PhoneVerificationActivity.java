package com.example.abhishek.e_farmer;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import junit.framework.TestResult;

import java.util.concurrent.TimeUnit;

public class PhoneVerificationActivity extends AppCompatActivity {

    String otpByFirebase;
    EditText phoneText,otpText;
    static String title;
    Button sendButton, verifyButton;
    static PhoneAuthCredential phoneDetails;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks stateChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        Intent intent = getIntent();
        title = intent.getStringExtra("value");
        getSupportActionBar().setTitle(title);

        phoneText = findViewById(R.id.phoneText);
        otpText = findViewById(R.id.otpText);

        sendButton = findViewById(R.id.sendButton);
        verifyButton = findViewById(R.id.verifyButton);

        otpText.setEnabled(false);
        verifyButton.setEnabled(false);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Number", phoneText.getText().toString());
                assignStateChange();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneText.getText().toString(), 60, TimeUnit.SECONDS, PhoneVerificationActivity.this,stateChange);
            }
        });
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential phoneAuthProvider = PhoneAuthProvider.getCredential(otpByFirebase, otpText.getText().toString());
                if(phoneAuthProvider.getSmsCode().equals(otpText.getText().toString())) {
                    Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                    intent.putExtra("Number",phoneText.getText().toString());
                    phoneDetails = phoneAuthProvider;
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wrong OTP",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void assignStateChange()
    {
        stateChange = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                otpText.setEnabled(true);
                verifyButton.setEnabled(true);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.i("Status","Error In Connecting");
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.i("OTP Sent is", s);
                otpByFirebase = s;
                sendButton.setEnabled(false);
                otpText.setEnabled(true);
                verifyButton.setEnabled(true);
                // phoneText.setEnabled(false);
            }
        };
    }
}
