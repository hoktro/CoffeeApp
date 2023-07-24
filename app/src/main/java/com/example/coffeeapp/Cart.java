package com.example.coffeeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class Cart extends AppCompatActivity {

    private OrderAdapter orderAdapter;
    private CoffeeDBHelper dbHelper = new CoffeeDBHelper(this);
    private int orderID;
    private double totalBill = 0.00;
    private List<OrderDetails> orderList;
    private List<Coffee> coffeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_cart);

        // Set up RecycleView
        setupRecycleView();

        // Setup Listener
        setupOnClickListener();
    }

    protected void onResume() {
        super.onResume();
        // Call the update function here
        updateBill();
    }

    private void setupRecycleView() {

        RecyclerView recyclerView = findViewById(R.id.cartShow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch the coffee data from the database
        orderID = dbHelper.getOrderID();
        Log.d("Cart", String.valueOf(orderID) );
        Toast.makeText(this, "Clicked on " + String.valueOf(orderID), Toast.LENGTH_SHORT).show();
        orderList = dbHelper.getAllOrder(orderID);
        coffeeList = dbHelper.getAllCoffee( orderList );

        orderAdapter = new OrderAdapter( this, orderList, coffeeList );
        orderAdapter.setupSwipeToDelete(recyclerView);

        // Add spacing between items in the RecyclerView (use a desired spacing value in pixels)
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing); // Replace R.dimen.item_spacing with the dimension resource for your desired spacing
        recyclerView.addItemDecoration(new ItemSpacingDecoration(spacingInPixels));

        recyclerView.setAdapter(orderAdapter);
    }

    private void setupOnClickListener() {

        // Back button
        ImageButton backButton = findViewById( R.id.backButton02 );
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Checkout button
        RelativeLayout checkoutButton = findViewById( R.id.checkoutButton );
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.updateID();
                orderID = dbHelper.getOrderID();

                orderList.clear();
                coffeeList.clear();

                orderList = dbHelper.getAllOrder(orderID);
                coffeeList = dbHelper.getAllCoffee( orderList );

                orderAdapter.notifyDataSetChanged();

                updateBill();

                Log.d("Cart", "Click button " + String.valueOf(orderID) );
            }
        });
    }

    public void updateBill() {
        totalBill = dbHelper.getTotalBill( orderID );
        double roundedTotal = Math.round(totalBill * 100.0) / 100.0; // Round to two decimal places

        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format for two decimal places
        String formattedTotal = "$" + decimalFormat.format(roundedTotal);

        TextView totalView = findViewById( R.id.total_price );
        totalView.setText(formattedTotal);
    }
}