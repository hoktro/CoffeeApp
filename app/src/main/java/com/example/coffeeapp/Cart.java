package com.example.coffeeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class Cart extends AppCompatActivity {

    private OrderAdapter orderAdapter;
    private CoffeeDBHelper dbHelper = new CoffeeDBHelper(this);
    private int orderID;
    private double totalBill = 0.00;

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
        Toast.makeText(this, "Clicked on " + String.valueOf(orderID), Toast.LENGTH_SHORT).show();
        List<OrderDetails> orderList = dbHelper.getAllOrder(orderID);
        List<Coffee> coffeeList = dbHelper.getAllCoffee( orderList );

        orderAdapter = new OrderAdapter( orderList, coffeeList );
        recyclerView.setAdapter(orderAdapter);

    }

    private void setupOnClickListener() {
        ImageButton backButton = findViewById( R.id.backButton02 );
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateBill() {
        totalBill = dbHelper.getTotalBill( orderID );
        double roundedTotal = Math.round(totalBill * 100.0) / 100.0; // Round to two decimal places

        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format for two decimal places
        String formattedTotal = "$" + decimalFormat.format(roundedTotal);

        TextView totalView = findViewById( R.id.total_price );
        totalView.setText(formattedTotal);
    }
}