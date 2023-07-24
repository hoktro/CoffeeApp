package com.example.coffeeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final Context context;
    private List<OrderDetails> orderList;
    private List<Coffee> coffeeList;

    public OrderAdapter(Context context, List<OrderDetails> orderList, List<Coffee> coffeeList ) {
        this.context = context;
        this.orderList = orderList;
        this.coffeeList = coffeeList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        Log.d("OrderAdapter", "onCreateViewHolder called");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        Log.d("OrderAdapter", "onBindViewHolder called for position: " + position);
        OrderDetails details = orderList.get(position);
        Coffee coffee = coffeeList.get(position);

        holder.orderImageView.setImageResource( coffee.getImageResourceId() );
        holder.orderName.setText( coffee.getName() );
        holder.orderAmount.setText( "x " + String.valueOf(details.getAmount()) );

        // Set text for bill
        double totalValue = details.getAmount() * ( coffee.getPrice() + 0.1 * details.getSize() );
        double roundedTotal = Math.round(totalValue * 100.0) / 100.0; // Round to two decimal places

        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format for two decimal places
        String formattedTotal = decimalFormat.format(roundedTotal);

        holder.orderTotal.setText("$" + formattedTotal);

        // Set text for description
        String describe = "";
        if( details.getShot() == 0 ) describe += "single | "; else describe += "double | ";
        if( details.getHot() == 0 ) describe += "hot | "; else  describe += "iced |";

        if( details.getSize() == 0 ) describe += "small";
        if( details.getSize() == 1 ) describe += "medium";
        if( details.getSize() == 2 ) describe += "large";

        if( details.getIce() == 1 ) describe += " | 30%";
        if( details.getIce() == 2 ) describe += " | 50%";
        if( details.getIce() == 3 ) describe += " | full ice";

        holder.orderDescription.setText( describe );
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView orderImageView;
        TextView orderName;
        TextView orderDescription;
        TextView orderAmount;
        TextView orderTotal;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderImageView = itemView.findViewById( R.id.coffeeImage );
            orderName = itemView.findViewById( R.id.coffeeName );
            orderDescription = itemView.findViewById( R.id.coffeeDescription );
            orderAmount = itemView.findViewById( R.id.coffeeAmount );
            orderTotal = itemView.findViewById( R.id.coffeeTotal );

        }
    }



    // Helper method to remove an item from the RecyclerView
    public void removeItem(int position) {

        CoffeeDBHelper dbHelper = new CoffeeDBHelper( context );
        dbHelper.removeOrder(orderList.get(position));

        orderList.remove(position);
        coffeeList.remove(position);

        // Call onItemDeleted in YourActivity
        if (context instanceof Cart) {
            ((Cart) context).updateBill();
        }

        notifyItemRemoved(position);
    }

    // Method to create and attach the swipe-to-delete functionality
    public void setupSwipeToDelete(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback swipeToDeleteCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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
                        .addSwipeLeftBackgroundColor( ContextCompat.getColor( context, R.color.delete_color) )
                        .addSwipeLeftCornerRadius(TypedValue.COMPLEX_UNIT_DIP, 15 )
                        .addSwipeLeftActionIcon(R.drawable.delete_icon)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
