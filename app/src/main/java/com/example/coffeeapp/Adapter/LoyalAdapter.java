package com.example.coffeeapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeapp.R;

public class LoyalAdapter extends RecyclerView.Adapter<LoyalAdapter.LoyalViewHolder> {
    private int OnGoing; // 0 OnGoing 1 History
    private int max_cup = 8;

    public LoyalAdapter( int Ongoing) {
        this.OnGoing = Ongoing;
    }

    @NonNull
    @Override
    public LoyalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Log.d("LoyalAdapter", "onCreateViewHolder called");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loyal_item, parent, false);
        return new LoyalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LoyalAdapter.LoyalViewHolder holder, int position) {
        // Log.d("LoyalAdapter", "onBindViewHolder called for position: " + position);
        if( position < OnGoing ) holder.itemView.setBackgroundResource(R.drawable.loyal_on);
        else holder.itemView.setBackgroundResource(R.drawable.loyal_off);
    }

    @Override
    public int getItemCount() {
        return max_cup;
    }

    static class LoyalViewHolder extends RecyclerView.ViewHolder {
        ImageView loyal_item;

        LoyalViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void changOnGoing( int current ) { OnGoing = current; }

}
