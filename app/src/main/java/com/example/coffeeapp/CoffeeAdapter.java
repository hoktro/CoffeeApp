package com.example.coffeeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder> {
    private List<Coffee> coffeeList;

    public CoffeeAdapter(List<Coffee> coffeeList) {
        this.coffeeList = coffeeList;
    }

    @NonNull
    @Override
    public CoffeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coffee_item, parent, false);
        return new CoffeeViewHolder(itemView);
    }

    // Setting item listener
    public interface OnItemClickListener {
        void onItemClick(Coffee coffee);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeViewHolder holder, int position) {
        Coffee coffee = coffeeList.get(position);
        holder.coffeeImageView.setImageResource(coffee.getImageResourceId());
        holder.coffeeNameTextView.setText(coffee.getName());

        // Set click listener for the coffee item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(coffee);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return coffeeList.size();
    }


    static class CoffeeViewHolder extends RecyclerView.ViewHolder {
        ImageView coffeeImageView;
        TextView coffeeNameTextView;

        CoffeeViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImageView = itemView.findViewById(R.id.coffeeImageView);
            coffeeNameTextView = itemView.findViewById(R.id.coffeeNameTextView);
        }
    }
}
