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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<OrderHistory> historyList;
    private Context context;
    private int OnGoing; // 0 OnGoing 1 History

    public HistoryAdapter( Context context, List<OrderHistory> historyList, int OnGoing ) {
        this.context = context;
        this.historyList = historyList;
        this.OnGoing = OnGoing;
        Log.d("HistoryAdapter", String.valueOf(OnGoing));
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Log.d("HistoryAdapter", "onCreateViewHolder called");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        // Log.d("HistoryAdapter", "onBindViewHolder called for position: " + position);
        Log.d("HistoryAdapter", String.valueOf(OnGoing));
        if( OnGoing == 1 ) {
            Log.d("HistoryAdapter", "change colorrrrr " );
            holder.historyDescribe.setTextColor(Color.parseColor("#D8D8D8"));
            holder.historyTotal.setTextColor(Color.parseColor("#D8D8D8"));
            holder.historyAddress.setTextColor(Color.parseColor("#D8D8D8"));
        }
        else {

            Log.d("HistoryAdapter", "It fucking bull shit");
            holder.historyDescribe.setTextColor(Color.parseColor("#001833"));
            holder.historyTotal.setTextColor(Color.parseColor("#001833"));
            holder.historyAddress.setTextColor(Color.parseColor("#001833"));
        }

        OrderHistory history = historyList.get(position);
        int ID = history.getOrderID();

        holder.historyTime.setText( history.getTime() );
        holder.historyAddress.setText( history.getAddress() );

        // Set text for bill
        CoffeeDBHelper dbHelper = new CoffeeDBHelper( context );
        double totalValue = dbHelper.getTotalBill( ID );
        double roundedTotal = Math.round(totalValue * 100.0) / 100.0; // Round to two decimal places

        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format for two decimal places
        String formattedTotal = decimalFormat.format(roundedTotal);

        holder.historyTotal.setText("$" + formattedTotal);

        // Set text for description

        holder.historyDescribe.setText( dbHelper.getDescription( ID ) );
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView historyTime;
        TextView historyDescribe;
        TextView historyAddress;
        TextView historyTotal;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            historyTime = itemView.findViewById(R.id.historyTime);
            historyDescribe = itemView.findViewById(R.id.historyDescribe);
            historyAddress = itemView.findViewById(R.id.historyAddress);
            historyTotal = itemView.findViewById(R.id.historyTotal);

        }
    }

    public void changOnGoing() { OnGoing = 1 - OnGoing; }

    // Helper method to remove an item from the RecyclerView
    public void removeItem(int position) {

        CoffeeDBHelper dbHelper = new CoffeeDBHelper( context );
        dbHelper.removeHistory(historyList.get(position).getOrderID());

        historyList.remove(position);

        // Call onItemDeleted in YourActivity
//        if (context instanceof MyOrder) {
//            ((MyOrder) context).updateHistory();
//        }

        notifyItemRemoved(position);
    }

    // Method to create and attach the swipe-to-delete functionality
    public void setupSwipeToDelete(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback swipeToDeleteCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                removeItem(position);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}