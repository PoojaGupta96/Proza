package com.example.jgiclubs.entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jgiclubs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private EditText signupEmail, signupPassword, signupName, signupUsn, signupNumber,renterPassword;
    private Button signupButton;

    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupName = findViewById(R.id.Username);
        signupUsn = findViewById(R.id.usn);
        signupNumber = findViewById(R.id.number);
        signupEmail = findViewById(R.id.mail);
        signupPassword = findViewById(R.id.password);
        renterPassword = findViewById(R.id.reenterpassword);
        signupButton = findViewById(R.id.SignUpBut);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();

                String name = signupName.getText().toString();
                String usn = signupUsn.getText().toString();
                String number = signupNumber.getText().toString();
                String mail = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();
                String renpassword = renterPassword.getText().toString();


                if(name.isEmpty() || usn.isEmpty() || number.isEmpty()||mail.isEmpty()|| password.isEmpty()||renpassword.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(renpassword)){
                    Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usn)){
                                Toast.makeText(SignUpActivity.this, "Usn is already registered!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                dbref.child("users").child(usn).child("Name").setValue(name);
                                dbref.child("users").child(usn).child("Mail").setValue(mail);
                                dbref.child("users").child(usn).child("Number").setValue(number);
                                dbref.child("users").child(usn).child("Password").setValue(password);
                                Toast.makeText(SignUpActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }


}