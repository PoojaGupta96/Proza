package com.example.jgiclubs.events;

import static com.example.jgiclubs.DatabaseUtil.getDescriptionsAsList;
import static com.example.jgiclubs.DatabaseUtil.getEventName;
import static com.example.jgiclubs.DatabaseUtil.getImageAsList;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jgiclubs.DatabaseUtil;
import com.example.jgiclubs.DescriptionImageAdapter;
import com.example.jgiclubs.DescriptionTextAdapter;
import com.example.jgiclubs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class EventDetails extends AppCompatActivity implements ClickListener {

    RecyclerView recyclerViewTexts, recyclerViewImages;
    DescriptionTextAdapter textAdapter;
    DescriptionImageAdapter imageAdapter;
    FirebaseStorage storage;
    FirebaseDatabase database;
    List<String> images,headings, descriptions;
    String eventName="", id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
       
        // Find Views
        recyclerViewTexts = findViewById(R.id.recyclerViewTexts);
        recyclerViewImages = findViewById(R.id.recyclerViewImages);
        TextView textViewEventName = findViewById(R.id.textViewEventName);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        getEventName(id).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.getValue(String.class);
                        // Use the eventName as needed
                        System.out.println("Event Name: " + name);
                        eventName =name;
                        textViewEventName.setText(eventName);
                        

                    } else {
                        System.out.println("No data found for the key.");

                    }
                } else {
                    System.out.println("Error getting data: " + task.getException());
                }
            }
        });


        headings = new ArrayList<>();
        descriptions = new ArrayList<>();

        // Example Image Data (Drawable Resource IDs)
        images = new ArrayList<>();
        loadImages();
        loadHeadingDescription();

        // Set up Text RecyclerView
        textAdapter = new DescriptionTextAdapter(headings, descriptions);
        recyclerViewTexts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTexts.setAdapter(textAdapter);

        // Set up Image RecyclerView
        imageAdapter = new DescriptionImageAdapter(images,this);
        recyclerViewImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewImages.setAdapter(imageAdapter);
    }

    private void loadImages() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("admin/Events/"+id+"/Details/Images/");
        getImageAsList(ref, new DatabaseUtil.ImageListCallback() {
            @Override
            public void onSuccess(List<String> imageUrls) {
                // Do something with the list of image URLs
                for (String url : imageUrls) {
                    Log.d("Image URL", url);
                    images.add(url);
                    imageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", errorMessage);
            }
        });
    }

    private void loadHeadingDescription(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("admin/Events/"+id+"/Details/descriptions/");
        getDescriptionsAsList(ref, new DatabaseUtil.DescListCallback() {
            @Override
            public void onSuccess(List<String[]> imageUrls) {
                // Do something with the list of image URLs
                for (String[] data : imageUrls) {
                    Log.d("Image URL", data[0]);
                    headings.add(data[0]);
                    descriptions.add(data[1]);
                    textAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", errorMessage);
            }
        });
    }

    @Override
    public void click(String url) {
        Intent intent = new Intent(EventDetails.this,ImageViewer.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }



}
