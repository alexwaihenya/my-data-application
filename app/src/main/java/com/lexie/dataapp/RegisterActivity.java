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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText fullName, phone, emailAddress, pass;
    private TextView signIn;
    private Button registerButton;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        intializeFields();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }


    private void intializeFields() {

        fullName = findViewById(R.id.textInputFullName);
        phone = findViewById(R.id.textInputPhone);
        emailAddress = findViewById(R.id.textInputEmailAddress);
        pass = findViewById(R.id.textInputPassword);
        signIn = findViewById(R.id.text_new_account);
        registerButton = findViewById(R.id.regButton);


    }

    private void registerUser() {
        String name = fullName.getText().toString();
        String phoneNumber = phone.getText().toString();
        String email = emailAddress.getText().toString();
        String password = pass.getText().toString();

        if (name.isEmpty()) {
            fullName.setError("FullName required!");
            fullName.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty()) {
            phone.setError("Phone Number required!");
            phone.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailAddress.setError("Email required!");
            emailAddress.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAddress.setError("Please provide valid email");
            emailAddress.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Password required!");
            pass.requestFocus();
            return;
        }
        if (password.length() < 6) {
            pass.setError("Min password length should be 6 characters!");
            pass.requestFocus();

        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, phoneNumber, email, password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        sendUserToMainActivity();
                                        Toast.makeText(RegisterActivity.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();

                                    } else {
                                        String message = task.getException().toString();
                                        Toast.makeText(RegisterActivity.this, "Error : " +message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(RegisterActivity.this, "Error : " +message, Toast.LENGTH_SHORT).show();

                        }


                    }
                });

    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

}
