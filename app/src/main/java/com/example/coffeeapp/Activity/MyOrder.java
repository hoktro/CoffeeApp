package com.example.coffeeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.coffeeapp.Adapter.HistoryAdapter;
import com.example.coffeeapp.Class.OrderHistory;
import com.example.coffeeapp.CoffeeDBHelper;
import com.example.coffeeapp.Decor.ItemSpacingDecoration;
import com.example.coffeeapp.R;

import java.util.List;

public class MyOrder extends AppCompatActivity {

    private CoffeeDBHelper dbHelper = new CoffeeDBHelper(this);
    private HistoryAdapter historyAdapter;
    int OnGoing;
    private List<OrderHistory> historyList;

    private TextView ongoingButton;
    private TextView historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_my_order);

        ongoingButton = findViewById( R.id.ongoingButton );
        historyButton = findViewById(R.id.historyButton);

        setupRecycleView();

        setupListener();
    }

    private void setupRecycleView() {

        RecyclerView recyclerView = findViewById(R.id.historyRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch the coffee data from the database
        historyList = dbHelper.getOnGoing();

        historyAdapter = new HistoryAdapter( this, historyList, OnGoing );
        historyAdapter.setupSwipeToDelete( recyclerView );

        // Add spacing between items in the RecyclerView (use a desired spacing value in pixels)
        int spacingInPixels = 0; // Replace R.dimen.item_spacing with the dimension resource for your desired spacing
        recyclerView.addItemDecoration(new ItemSpacingDecoration(spacingInPixels));

        recyclerView.setAdapter(historyAdapter);
    }

    private void setupListener() {

        ongoingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( OnGoing == 1 ) {

                    OnGoing = 0;
                    historyList.clear();
                    historyList.addAll(dbHelper.getOnGoing());
                    historyAdapter.changOnGoing();
                    historyAdapter.notifyDataSetChanged();

                    ongoingButton.setBackground(getDrawable(R.drawable.black_line));
                    historyButton.setBackground(null);

                    ongoingButton.setTextColor(Color.parseColor("#001833"));
                    historyButton.setTextColor(Color.parseColor("#D8D8D8"));
                }
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( OnGoing == 0 ) {

                    OnGoing = 1;
                    Log.d("MyOrder", String.valueOf(OnGoing));
                    historyAdapter.changOnGoing();
                    historyList.clear();
                    historyList.addAll(dbHelper.getHistory());
                    historyAdapter.notifyDataSetChanged();

                    historyButton.setBackground(getDrawable(R.drawable.black_line));
                    ongoingButton.setBackground(null);

                    historyButton.setTextColor(Color.parseColor("#001833"));
                    ongoingButton.setTextColor(Color.parseColor("#D8D8D8"));
                }
            }
        });

        ImageButton homepageButton = findViewById( R.id.homeButton_myorder );
        homepageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MyOrder.this, HomePage.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        ImageButton redeemButton = findViewById( R.id.redeemButton_myorder );
        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MyOrder.this, RewardsScreen.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void updateHistory() {
        OnGoing = 0;
        historyList.clear();
        historyList.addAll(dbHelper.getOnGoing());
        historyAdapter.changOnGoing();
        historyAdapter.notifyDataSetChanged();

        ongoingButton.setBackground(getDrawable(R.drawable.black_line));
        historyButton.setBackground(null);

        ongoingButton.setTextColor(Color.parseColor("#001833"));
        historyButton.setTextColor(Color.parseColor("#D8D8D8"));
    }
}