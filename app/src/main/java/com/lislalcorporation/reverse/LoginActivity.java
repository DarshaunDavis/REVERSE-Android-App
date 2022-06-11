package com.lislalcorporation.reverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText entEmail, entPassword;
    ProgressBar progressbar;
    Button signIn;
    TextView recPassword;
    String strEmail, strPassword;
    String emailPattern = "[a-zA-Z0-9_-]+@[a-z]+\\.[a-z]+";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        entEmail = findViewById(R.id.editTextEmailAddress);
        entPassword = findViewById(R.id.editTextPassword);
        progressbar = findViewById(R.id.loginProgressBar);
        signIn = findViewById(R.id.signInButton);
        recPassword = findViewById(R.id.recoverPassword);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(LoginActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strEmail = entEmail.getText().toString().trim();
                strPassword = entPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(strEmail)) {
                    if (strEmail.matches(emailPattern)) {
                        if (!TextUtils.isEmpty(strPassword)) {
                            LoginUser();
                        } else {
                            entPassword.setError("Password field can't be empty");
                        }

                    } else {
                        entEmail.setError("Enter a valid Email");
                    }
                } else {
                    entEmail.setError("Email field can't be empty");
                }
            }
        });

        recPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PasswordRecoveryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void LoginUser() {
        progressbar.setVisibility(View.VISIBLE);
        signIn.setVisibility(View.INVISIBLE);
        
        mAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Login was successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.INVISIBLE);
                signIn.setVisibility(View.VISIBLE);
            }
        });
    }
}