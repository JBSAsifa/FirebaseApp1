package com.example.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {
    EditText emailid1 , passid1;
    Button Btnreg1;
    ProgressBar btnprog1;
    TextView Btnlog;
    FirebaseAuth mAuth1;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth1.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Registration.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        emailid1 = findViewById(R.id.email1);
        passid1 = findViewById(R.id.pass1);
        btnprog1 =findViewById(R.id.prog1);
        Btnreg1 = findViewById(R.id.regbtn);
        Btnlog = findViewById(R.id.lognow);
        mAuth1 = FirebaseAuth.getInstance();

        Btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SecondActivity
                Intent intent = new Intent(Registration.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Btnreg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnprog1.setVisibility(View.VISIBLE);
                String email, password;
                email = emailid1.getText().toString();
                password = passid1.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registration.this, "Enter Email", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Registration.this, "Enter Password", Toast.LENGTH_LONG).show();
                }

                mAuth1.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                btnprog1.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(Registration.this, "Registration Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Registration.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {

                                    Toast.makeText(Registration.this, "Registration failed.",
                                            Toast.LENGTH_SHORT).show();

//                                    String error = task.getException() != null ? task.getException().getMessage() : "Authentication failed.";
//                                    Toast.makeText(Registration.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
            });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}