package com.example.jgiclubs.events;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.jgiclubs.databinding.ActivityImageViewerBinding;

import com.example.jgiclubs.R;
import com.squareup.picasso.Picasso;

public class ImageViewer extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_viewer);
        ImageView img_field = findViewById(R.id.fullscreen_image);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");


        Picasso.get()
                .load(url)
                .placeholder(R.drawable.bulb) // Optional placeholder while loading
                .error(R.drawable.bulb) // Optional error image
                .into(img_field);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_image_viewer);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}