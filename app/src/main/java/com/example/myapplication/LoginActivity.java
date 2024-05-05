package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
     DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://academybox-f8b53-default-rtdb.firebaseio.com/");
     private EditText loginusn, loginPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginusn = findViewById(R.id.username);
        loginPassword = findViewById(R.id.password);
        Button loginbutton = findViewById(R.id.LoginButton);

        TextView signUpRedirectText = findViewById(R.id.signupredierect);
        loginbutton.setOnClickListener(view -> {
            String usn = loginusn.getText().toString();
            String pass = loginPassword.getText().toString();
            if (usn.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter your mobile or password", Toast.LENGTH_SHORT).show();
            }
            else {
                dbref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(usn)){
                            final String getpass = snapshot.child(usn).child("Password").getValue(String.class);
                            assert getpass != null;
                            if(getpass.equals(pass)){
                                Toast.makeText(LoginActivity.this, "Succesfully Logged In", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        signUpRedirectText.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,SignUpActivity.class)));
    }

}