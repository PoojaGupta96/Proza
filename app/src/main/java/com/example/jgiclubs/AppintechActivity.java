package com.example.jgiclubs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AppintechActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appintech_redirect);
        LinearLayout event_redirect1 = findViewById(R.id.club_event_1);
        LinearLayout event_redirect2 = findViewById(R.id.club_event_2);
        ImageButton back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppintechActivity.this,ClubList.class));
                finish();
            }
        });
        event_redirect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppintechActivity.this,activity_appintent_event1.class));
                finish();
            }
        });
        event_redirect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppintechActivity.this,activity_appintech_event2.class));
                finish();
            }
        });
    }
}