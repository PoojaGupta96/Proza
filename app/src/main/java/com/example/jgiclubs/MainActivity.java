package com.example.jgiclubs;

import static com.example.jgiclubs.R.id;
import static com.example.jgiclubs.R.layout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_list);
        LinearLayout foss_redirect = findViewById(R.id.FOSS_Club);
        LinearLayout appintech_redirect = findViewById(R.id.appintech);
        LinearLayout tycoon_redirect = findViewById(R.id.tycoon);
        foss_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,FossRedirectActivity.class));
//                finish();
            }
        });
        appintech_redirect.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity((new Intent(MainActivity.this,AppintechActivity.class)));
//                finish();
            }
        }));
        tycoon_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TycoonActivity.class));
//                finish();
            }
        });
    }
}