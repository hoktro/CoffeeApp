package com.example.coffeeapp.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeapp.CoffeeDBHelper;
import com.example.coffeeapp.Class.OrderHistory;
import com.example.coffeeapp.R;

import java.text.DecimalFormat;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.RewardsViewHolder> {
    private List<OrderHistory> historyList;
    private Context context;

    public RewardsAdapter( Context context, List<OrderHistory> historyList ) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Log.d("RewardsAdapter", "onCreateViewHolder called");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item, parent, false);
        return new RewardsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsAdapter.RewardsViewHolder holder, int position) {
        // Log.d("RewardsAdapter", "onBindViewHolder called for position: " + position);
        // Log.d("RewardsAdapter", String.valueOf(OnGoing));

        OrderHistory history = historyList.get(position);
        int ID = history.getOrderID();

        holder.rewardsTime.setText( history.getTime() );

        // Set text for bill
        CoffeeDBHelper dbHelper = new CoffeeDBHelper( context );
        double totalValue = dbHelper.getTotalBill( ID );
        double roundedTotal = Math.round(totalValue); // Round to two decimal places

        DecimalFormat decimalFormat = new DecimalFormat("#"); // Format for two decimal places
        String formattedTotal = decimalFormat.format(roundedTotal);

        holder.rewardsPoints.setText( "+ " + formattedTotal + " Pts");

        // Set text for description

        holder.rewardsDescribe.setText( dbHelper.getDescription( ID ) );
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class RewardsViewHolder extends RecyclerView.ViewHolder {
        TextView rewardsTime;
        TextView rewardsDescribe;
        TextView rewardsPoints;

        RewardsViewHolder(@NonNull View itemView) {
            super(itemView);

            rewardsTime = itemView.findViewById(R.id.rewardsTime);
            rewardsDescribe = itemView.findViewById(R.id.rewardsDescribe);
            rewardsPoints = itemView.findViewById(R.id.rewardsPoints);

        }
    }
}