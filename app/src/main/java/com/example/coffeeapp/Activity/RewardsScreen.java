package com.example.coffeeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeapp.Adapter.LoyalAdapter;
import com.example.coffeeapp.Adapter.RewardsAdapter;
import com.example.coffeeapp.Class.OrderHistory;
import com.example.coffeeapp.CoffeeDBHelper;
import com.example.coffeeapp.Decor.ItemSpacingDecoration;
import com.example.coffeeapp.R;

import java.util.List;

public class RewardsScreen extends AppCompatActivity {

    private CoffeeDBHelper dbHelper = new CoffeeDBHelper(this);
    private RewardsAdapter rewardsAdapter;
    private LoyalAdapter loyalAdapter;
    private List<OrderHistory> historyList;

    private int loyal_point = 4;
    private int redeem_point = 0;

    private TextView redeemPoint;
    private TextView loyalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_rewards_screen);

        //
        redeemPoint = findViewById( R.id.redeemPoint_rewards);
        loyalCount = findViewById( R.id.loyal_count_rewards );

        updateScreen();
        setupRecycleView();
        setupOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateScreen();
    }

    private void setupRecycleView() {

        RecyclerView recyclerView = findViewById(R.id.historyRewards_rewards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch the coffee data from the database
        historyList = dbHelper.getRewardsHistory();

        rewardsAdapter = new RewardsAdapter( this, historyList );

        // Add spacing between items in the RecyclerView (use a desired spacing value in pixels)
        int spacingInPixels = 0; // Replace R.dimen.item_spacing with the dimension resource for your desired spacing
        recyclerView.addItemDecoration(new ItemSpacingDecoration(spacingInPixels));

        recyclerView.setAdapter(rewardsAdapter);

        // Loyal card
        loyal_point = dbHelper.getLoyalPoint();
        RecyclerView loyalView = findViewById(R.id.loyal_rewards);
        loyalView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loyalAdapter = new LoyalAdapter( loyal_point );
        loyalView.setAdapter(loyalAdapter);
    }
    private void setupOnClickListener() {

        ImageButton historyButton = findViewById( R.id.historyButton_rewards );
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( RewardsScreen.this, MyOrder.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        ImageButton homeButton = findViewById( R.id.homeButton_rewards );
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( RewardsScreen.this, HomePage.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        TextView rewardsButton = findViewById( R.id.redeem_rewards );
        rewardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( RewardsScreen.this, RedeemRewardsScreen.class );
                startActivity(intent);
            }
        });

        RelativeLayout card = findViewById( R.id.loyal_card_rewards );
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( loyal_point == 8 ) {
                    Intent intent = new Intent( RewardsScreen.this, RedeemRewardsScreen.class );
                    intent.putExtra("mode", 1 );
                    startActivity(intent);
                }
            }
        });
    }

    private void updateScreen() {
        redeem_point = dbHelper.getRedeemPoint();
        loyal_point = dbHelper.getLoyalPoint();
        redeemPoint.setText( String.valueOf(redeem_point));
        loyalCount.setText( String.valueOf(loyal_point) + " / 8");
    }
}