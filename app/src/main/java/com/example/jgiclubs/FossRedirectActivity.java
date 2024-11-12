package com.example.jgiclubs;

import static com.example.jgiclubs.DatabaseUtil.getEventListData;
import static com.example.jgiclubs.DatabaseUtil.getPastEventIds;
import static com.example.jgiclubs.DatabaseUtil.getUpcomingEventIds;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jgiclubs.events.EventDetails;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

 public class FossRedirectActivity extends AppCompatActivity implements onClickListener {

    private TabLayout tabLayout;

    int data =0;
    RecyclerView recyclerView;
    private ArrayList<EventListData> item;
    private Set<EventListData> set;
    EventsListRVAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.foss_redirect);

        tabLayout = findViewById(R.id.tabLayout);

        // Attach TabLayout with ViewPager2
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.addTab(tabLayout.newTab().setText("Past"));



        item = new ArrayList<>();
        set = new HashSet<>();


        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventsListRVAdapter(item,this);
        recyclerView.setAdapter(adapter);
        fillItemList(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                fillItemList(tab.getPosition());
            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(@NonNull TabLayout.Tab tab) {}
        });

    }

    @Override
    public void onClick(String id) {
        Intent intent = new Intent(this, EventDetails.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    private void fillItemList(int data) {
        if (data == 1) {// past
            item.clear();
            adapter.notifyDataSetChanged();
            getPastEventIds(new DatabaseUtil.DataCallback() {
                @Override
                public void onCallback(ArrayList<String> eventIds) {
                    System.out.println("Past Events: ---------" + eventIds);
                    for (String id : eventIds) {
                        getEventListData(id, new DatabaseUtil.EventDataCallback() {
                            @Override
                            public void onCallback(EventListData event) {
                                if (!set.contains(event)) {
                                    item.add(event);
                                    adapter.notifyDataSetChanged();
                                    set.add(event);
                                    System.out.println(event.content);
                                }
                            }
                        });
                    }
                }
            });
        } else { // upcoming events
            item.clear();
            adapter.notifyDataSetChanged();
            getUpcomingEventIds(new DatabaseUtil.DataCallback() {
                @Override
                public void onCallback(ArrayList<String> eventIds) {
                    System.out.println("Upcoming Events: ---------" + eventIds);
                    for (String id : eventIds) {
                        getEventListData(id, new DatabaseUtil.EventDataCallback() {
                            @Override
                            public void onCallback(EventListData event) {
                                if (!set.contains(event)) {
                                    item.add(event);
                                    adapter.notifyDataSetChanged();
                                    set.add(event);
                                    System.out.println(event.content);
                                }
                            }
                        });
                    }
                }
            });
        }

    }

}
class EventListData {
    public String id;
    public String content;

    public String date, month;

    public EventListData(String id, String content,  String date, String month) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.month = month;
    }

    @Override
    public String toString() {
        return content;
    }
}
interface onClickListener{
    void onClick(String url);
}

