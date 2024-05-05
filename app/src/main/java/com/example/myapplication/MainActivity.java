package com.example.myapplication;

import static com.example.myapplication.R.id;
import static com.example.myapplication.R.layout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);


        Button donebutton = findViewById(id.DoneButton);
        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    startActivity(new Intent(MainActivity.this,ClubList.class));
                    finish();

            }
        });
    }
}