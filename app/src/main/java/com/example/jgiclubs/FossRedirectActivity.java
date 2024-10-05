package com.example.jgiclubs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class FossRedirectActivity extends AppCompatActivity {
    private LinearLayout activity_1;
    private LinearLayout event_activity;
    private LinearLayout activity_2;
    private LinearLayout parent_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.foss_redirect);
        SwitchCompat switchCompat = findViewById(R.id.switchs);
        event_activity = findViewById(R.id.event_activity);
        activity_1  =  findViewById(R.id.up_club_event_1);
        activity_2  =  findViewById(R.id.up_club_event_2);
        parent_layout = findViewById(R.id.parent_layout);
        ImageButton back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FossRedirectActivity.this,ClubList.class));
                finish();
            }
        });
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    swapLayouts(activity_1, event_activity);
                } else {
                    swapLayouts(event_activity, activity_1);
                }
            }
        });

        activity_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FossRedirectActivity.this, activity_foss_event1.class ));
                finish();
            }
        });
        activity_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FossRedirectActivity.this, activity_foss_event1.class ));
                finish();
            }
        });
    }


    private void swapLayouts(LinearLayout visibleLayout, LinearLayout invisibleLayout) {
        if(invisibleLayout.getVisibility() == View.INVISIBLE) {
            invisibleLayout.setVisibility(View.VISIBLE);
            visibleLayout.setVisibility(View.VISIBLE);
        }else {
            invisibleLayout.setVisibility(View.INVISIBLE);
            visibleLayout.setVisibility(View.INVISIBLE);
        }


    }
}
