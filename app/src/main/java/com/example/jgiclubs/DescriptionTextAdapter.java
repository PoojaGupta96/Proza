package com.example.jgiclubs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DescriptionTextAdapter extends  RecyclerView.Adapter<DescriptionTextAdapter.TextViewHolder> {

private List<String> headings;
private List<String> descriptions;

public DescriptionTextAdapter(List<String> headings, List<String> descriptions) {
    this.headings = headings;
    this.descriptions = descriptions;
}

@NonNull
@Override
public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
    return new TextViewHolder(view);
}


    @Override
public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
    holder.textViewHeading.setText(headings.get(position));
    holder.textViewDescription.setText(descriptions.get(position));
}

@Override
public int getItemCount() {
    return Math.min(headings.size(), descriptions.size()); // Match smaller size if lists differ
}

static class TextViewHolder extends RecyclerView.ViewHolder {
    TextView textViewHeading, textViewDescription;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewHeading = itemView.findViewById(R.id.textViewHeading);
        textViewDescription = itemView.findViewById(R.id.textViewDescription);
    }
}
}