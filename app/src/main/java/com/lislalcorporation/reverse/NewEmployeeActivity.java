package com.lislalcorporation.reverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NewEmployeeActivity extends AppCompatActivity {

    EditText entFirstName, entLastName, entID, entStreet, entApt, entCity, entState, entPEmail, entCEmail;
    TextView logoutButton;
    ProgressBar progressbar;
    Button addNew, exitButton;
    String strFirstName, strLastName, strID, strStreet, strApt, strCity, strState, strPEmail, strCEmail, strPassword;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_employee);

        entFirstName = findViewById(R.id.editTextFirstName);
        entLastName = findViewById(R.id.editTextLastName);
        entID = findViewById(R.id.editTextEmployeeID);
        entStreet = findViewById(R.id.editTextEmployeeID);
        entApt = findViewById(R.id.editTextEmployeeApt);
        entCity = findViewById(R.id.editTextEmployeeCity);
        entState = findViewById(R.id.editTextEmployeeState);
        entPEmail = findViewById(R.id.editTextEmailAddressPersonal);
        entCEmail = findViewById(R.id.editTextEmailAddressCompany);
        progressbar = findViewById(R.id.newEmployeeProgressBar);
        addNew = findViewById(R.id.newEmployeeButton);
        exitButton = findViewById(R.id.exitNewEmployee);
        logoutButton = findViewById(R.id.logoutButtonNE);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(NewEmployeeActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(NewEmployeeActivity.this, "You are not connected", Toast.LENGTH_SHORT).show();
        }

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewEmployeeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(NewEmployeeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strFirstName = entFirstName.getText().toString().trim();
                strLastName = entLastName.getText().toString().trim();
                strID = entID.getText().toString().trim();
                strStreet = entStreet.getText().toString().trim();
                strApt  = entApt.getText().toString().trim();
                strCity  = entCity.getText().toString().trim();
                strState  = entState.getText().toString().trim();
                strPEmail  = entPEmail.getText().toString().trim();
                strCEmail  = entCEmail.getText().toString().trim();
                strPassword = generatePassword(12).trim();

                if (!TextUtils.isEmpty(strFirstName)) {
                    if (!TextUtils.isEmpty(strLastName)) {
                        if (!TextUtils.isEmpty(strID)) {
                            if (!TextUtils.isEmpty(strStreet)) {
                                if (!TextUtils.isEmpty(strApt)) {
                                    if (!TextUtils.isEmpty(strCity)) {
                                        if (!TextUtils.isEmpty(strState)) {
                                            if (!TextUtils.isEmpty(strPEmail)) {
                                                if (!TextUtils.isEmpty(strCEmail)) {
                                                    AddNewEmployee();
                                                } else {
                                                    entCEmail.setError("Employee Company Email field can't be empty");
                                                    Toast.makeText(NewEmployeeActivity.this, "Please enter Employee Company Email", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                entPEmail.setError("Employee Personal Email field can't be empty");
                                                Toast.makeText(NewEmployeeActivity.this, "Please enter Employee Personal Email", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            entState.setError("Employee State field can't be empty");
                                            Toast.makeText(NewEmployeeActivity.this, "Please enter Employee State", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        entCity.setError("Employee City field can't be empty");
                                        Toast.makeText(NewEmployeeActivity.this, "Please enter Employee City", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    entApt.setError("Employee Apt field can't be empty");
                                    Toast.makeText(NewEmployeeActivity.this, "Please enter Employee Apt", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                entStreet.setError("Employee Address field can't be empty");
                                Toast.makeText(NewEmployeeActivity.this, "Please enter Address Street", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            entID.setError("Employee ID field can't be empty");
                            Toast.makeText(NewEmployeeActivity.this, "Please enter Employee ID", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        entLastName.setError("Employee Last Name field can't be empty");
                        Toast.makeText(NewEmployeeActivity.this, "Please enter Employee Last Name", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    entFirstName.setError("Employee First Name field can't be empty");
                    Toast.makeText(NewEmployeeActivity.this, "Please enter Employee First Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void AddNewEmployee() {

        progressbar.setVisibility(View.VISIBLE);
        addNew.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(strCEmail,strPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                /*Map<String, Object> user = new HashMap<>();
                user.put("firstName", strFirstName);
                user.put("lastName", strLastName);
                user.put("employeeID", strID);
                user.put("address", strStreet);
                user.put("apartment", strApt);
                user.put("city", strCity);
                user.put("state", strState);
                user.put("personalEmail", strPEmail);
                user.put("companyEmail", strCEmail);*/


                Toast.makeText(NewEmployeeActivity.this, "New Employee was successfully added!", Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.INVISIBLE);
                addNew.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewEmployeeActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.INVISIBLE);
                addNew.setVisibility(View.VISIBLE);

            }
        });
    }

    private String generatePassword(int length) {
        char[] chars = "[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()<>?]".toCharArray();
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++)
        {
            char c = chars[r.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}