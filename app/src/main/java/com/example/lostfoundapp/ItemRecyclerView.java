package com.example.lostfoundapp;

import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemRecyclerView extends RecyclerView.Adapter<ItemRecyclerView.ViewHolder> {
    private static List<Item> items;
    private static OnItemClickListener listener;

    public ItemRecyclerView(List<Item> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item post = items.get(position);
        holder.titleTextView.setText(post.getType());
        holder.descriptionTextView.setText(post.getItemName() + ": "+ post.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;


        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView);
            descriptionTextView = view.findViewById(R.id.descriptionTextView);

            view.setOnClickListener(view1 -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Item post = items.get(position);
                    listener.onItemClick(post);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Item post);
    }


}

