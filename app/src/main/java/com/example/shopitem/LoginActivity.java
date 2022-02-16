package com.example.shopitem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout userEmailWrapper, userPasswordWrapper;

    EditText userEmail, userPassword;
    Button btnLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEmailWrapper=findViewById(R.id.userEmailWrapper);
        userPasswordWrapper=findViewById(R.id.userPasswordWrapper);

        userEmail=findViewById(R.id.userEmail);
        userPassword=findViewById(R.id.userPassword);

        btnLogin=findViewById(R.id.btnUserLogin);

        mAuth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=userEmail.getText().toString().trim();
                String password=userPassword.getText().toString().trim();

                if (email.isEmpty()){
                    userEmailWrapper.setError("Enter email.");
                    userEmailWrapper.requestFocus();
                    return;
                }

                if (password.isEmpty()){
                    userPasswordWrapper.setError("Enter Password");
                    userPasswordWrapper.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, ShopActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}