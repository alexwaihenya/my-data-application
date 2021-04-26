package com.lexie.dataapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText emailAddress,pass;
    private Button loginButton;
    private TextView signUp;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        intializeFields();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });



    }


    private void intializeFields() {

        emailAddress = findViewById(R.id.textInputLayoutEmailAddress);
        pass = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginBtn);
        signUp = findViewById(R.id.textViewNeedAccount);


    }
    private void loginUser() {

        String email = emailAddress.getText().toString();
        String password = pass.getText().toString();

        if (email.isEmpty()){
            emailAddress.setError("Email required!");
            emailAddress.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailAddress.setError("Please provide valid email!");
            emailAddress.requestFocus();
            return;
        }
        if (password.isEmpty()){
            pass.setError("Password required!");
            pass.requestFocus();
            return;
        }
        if (password.length() < 6){
            pass.setError("Min password length should be 6 characters!");
            pass.requestFocus();

        }
        else {
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                sendUserMainActivity();
                                Toast.makeText(LoginActivity.this,"Logged in successfully",Toast.LENGTH_LONG).show();
                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    private void sendUserMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(mainIntent);
    }

}