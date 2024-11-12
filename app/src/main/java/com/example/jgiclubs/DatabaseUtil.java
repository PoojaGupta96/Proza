package com.example.jgiclubs;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class DatabaseUtil {

    public static void getImageAsList(DatabaseReference ref, final ImageListCallback callback) {
        // Attach a listener to the reference to read the data from the Firestore Database
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if data exists
                if (dataSnapshot.exists()) {
                    // Read the list of image URLs from the dataSnapshot
                    List<String> imageUrls = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String imageUrl = snapshot.getValue(String.class);
                        imageUrls.add(imageUrl);
                    }
                    // Return the image URLs using the callback
                    callback.onSuccess(imageUrls);
                } else {
                    // If the data doesn't exist, return an empty list or handle as needed
                    callback.onFailure("No images found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occurred during data retrieval
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public static void getDescriptionsAsList(DatabaseReference ref, final DescListCallback callback) {
         ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if data exists
                if (dataSnapshot.exists()) {
                     List<String[]> descList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String desc = snapshot.getValue(String.class);
                        String heading = snapshot.getKey();
                        descList.add(new String[]{heading,desc});
                    }

                    callback.onSuccess(descList);
                } else {
                    callback.onFailure("No images found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occurred during data retrieval
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public static Task getEventName(String id) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("admin/Events/").child(id).child("Details").child("event_name");

        return ref.get();
    }

    public static void getUpcomingEventIds(final DataCallback callback) {
        long currentTime = System.currentTimeMillis() / 1000;
        DatabaseReference eventsRef =  FirebaseDatabase.getInstance().getReference("admin/Events");

        eventsRef.orderByChild("Details/time").startAt(currentTime).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> upcomingEventIds = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    String eventId = eventSnapshot.getKey();
                    upcomingEventIds.add(eventId);
                }
                callback.onCallback(upcomingEventIds);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                callback.onCallback(new ArrayList<>());
            }
        });
    }

    public static void getPastEventIds(final DataCallback callback) {
        long currentTime = System.currentTimeMillis() / 1000; // Convert to seconds
        DatabaseReference eventsRef =  FirebaseDatabase.getInstance().getReference("admin/Events");

        eventsRef.orderByChild("Details/time").endAt(currentTime ).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> pastEventIds = new HashSet<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    String eventId = eventSnapshot.getKey();
                    pastEventIds.add(eventId);
                }
                callback.onCallback(new ArrayList<>(pastEventIds));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCallback(new ArrayList<>());
            }
        });
    }

    public static void getEventListData(String id, final EventDataCallback callback) {
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("admin/Events").child(id).child("Details");
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                     String content = dataSnapshot.child("short_desc").getValue(String.class);
                    Long timestamp = dataSnapshot.child("time").getValue(Long.class);
                    if (timestamp == null) {
                        timestamp = 0L;
                    }

                     Date date = new Date(timestamp * 1000); // Convert to milliseconds
                    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                    SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
                    String day = dayFormat.format(date);
                    String month = monthFormat.format(date);

                     EventListData event = new EventListData(id, content, day, month);
                    callback.onCallback(event);
                } else {
                    callback.onCallback(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }


     public interface EventDataCallback {
        void onCallback(EventListData eventListData);
    }

     public interface DataCallback {
        void onCallback(ArrayList<String> eventIds);
    }





    public interface ImageListCallback {
        void onSuccess(List<String> imageUrls);
        void onFailure(String errorMessage);
    }

    public interface DescListCallback {
        void onSuccess(List<String[]> desc);
        void onFailure(String errorMessage);
    }

}
