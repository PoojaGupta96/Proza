package com.example.jgiclubs;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.jgiclubs.databinding.EventShortColor1Binding;
import com.example.jgiclubs.databinding.EventShortColor2Binding;
import java.util.List;


public class EventsListRVAdapter extends RecyclerView.Adapter<EventsListRVAdapter.ViewHolder> {

    private final List<EventListData> mValues;
    private final onClickListener listener;
    private boolean showFirstList = true;

    public EventsListRVAdapter(List<EventListData> items, onClickListener listener) {
        mValues = items;
        this.listener= listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(showFirstList) {
            showFirstList = !showFirstList;
            return new ViewHolder(EventShortColor1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }else{
            showFirstList = !showFirstList;
            return new ViewHolder(EventShortColor2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        EventListData val = mValues.get(position);
        holder.date.setText(val.date);
        holder.month.setText(val.month);
        holder.desc.setText(val.content);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(val.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView date, month, desc;
        public final LinearLayout layout;

        public ViewHolder(EventShortColor1Binding binding) {
            super(binding.getRoot());
            date = binding.date;
            month = binding.month;
            desc = binding.description;
            layout = binding.clickable;
        }

        public ViewHolder(EventShortColor2Binding binding) {
            super(binding.getRoot());
            date = binding.date;
            month = binding.month;
            desc = binding.description;
            layout = binding.clickable;
        }

    }
}