package com.example.jgiclubs.entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jgiclubs.R;

public class Login_type extends AppCompatActivity {
    Button userLogin, adminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_type);
        userLogin = findViewById(R.id.user_button);
        adminLogin = findViewById(R.id.admin_button);
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_type.this, LoginActivity.class));
            }
        });

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_type.this, AdminLogin.class));
            }
        });
    }
}