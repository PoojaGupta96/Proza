package com.example.jgiclubs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jgiclubs.events.ClickListener;
import com.example.jgiclubs.events.EventDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DescriptionImageAdapter extends RecyclerView.Adapter<DescriptionImageAdapter.ImageViewHolder> {

    private List<String> imageList;
    private ClickListener listener;

    public DescriptionImageAdapter(List<String> imageList, ClickListener listener) {
        this.imageList = imageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(imageList.get(position));
            }
        });
        Picasso.get()
                .load(imageList.get(position))
                .placeholder(R.drawable.bulb) // Optional placeholder while loading
                .error(R.drawable.bulb) // Optional error image
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);


        }
    }
}