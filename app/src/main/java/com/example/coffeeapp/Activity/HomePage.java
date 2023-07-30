package com.example.coffeeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coffeeapp.Adapter.CoffeeAdapter;
import com.example.coffeeapp.Adapter.LoyalAdapter;
import com.example.coffeeapp.Class.Coffee;
import com.example.coffeeapp.CoffeeDBHelper;
import com.example.coffeeapp.Decor.GridItemDecoration;
import com.example.coffeeapp.R;
import com.example.coffeeapp.Class.User;

import java.util.List;

public class HomePage extends AppCompatActivity implements CoffeeAdapter.OnItemClickListener {

    private CoffeeAdapter coffeeAdapter;
    private LoyalAdapter loyalAdapter;
    private User user;

    // Get an instance of the CoffeeDBHelper
    private CoffeeDBHelper dbHelper = new CoffeeDBHelper(this);
    private int loyal_point = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_home_page);

        // setup RecycleView
        // dbHelper.initializeCoffeeData();
        // dbHelper.initializeUserData();
        setupRecycleView();

        // setup Listener
        setupOnClickListener();

        // Update screen
        updateScreen();
        ImageButton homeButton = findViewById( R.id.homeButton_homepage );
        int color = Color.parseColor("#324A59");
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        homeButton.setBackgroundTintList(colorStateList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateScreen();
    }

    private void setupRecycleView() {

        // deleteDatabase();

        RecyclerView recyclerView = findViewById(R.id.dRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Fetch the coffee data from the database
        List<Coffee> coffeeListFromDb = dbHelper.getAllCoffee();

        coffeeAdapter = new CoffeeAdapter(coffeeListFromDb);
        recyclerView.setAdapter(coffeeAdapter);
        // Set the click listener for the CoffeeAdapter
        coffeeAdapter.setOnItemClickListener(this);

        // Set item decoration to center the children within each grid cell and add manual spacing between rows
        int horizontalSpacing = getResources().getDimensionPixelSize(R.dimen.horizontal_spacing); // Define horizontal spacing as a dimension in resources
        int verticalSpacing = getResources().getDimensionPixelSize(R.dimen.vertical_spacing); // Define vertical spacing as a dimension in resources
        int leftMargin = getResources().getDimensionPixelSize(R.dimen.left_margin); // Define left margin as a dimension in resources
        int rightMargin = getResources().getDimensionPixelSize(R.dimen.right_margin); // Define right margin as a dimension in resources

        recyclerView.addItemDecoration(new GridItemDecoration(horizontalSpacing, verticalSpacing, leftMargin, rightMargin));

        // Loyal card
        loyal_point = dbHelper.getLoyalPoint();
        RecyclerView loyalView = findViewById(R.id.loyal_homepage);
        loyalView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loyalAdapter = new LoyalAdapter( loyal_point );
        loyalView.setAdapter(loyalAdapter);

        TextView loyalCount = findViewById( R.id.loyal_count_homepage );
        loyalCount.setText( String.valueOf(loyal_point) + " / 8");
    }

    // Implement the onItemClick method of the OnItemClickListener interface

    public void onItemClick(Coffee coffee) {
        // Handle the click event for the coffee item
        // For example, you can show a toast with the coffee name
        // Toast.makeText(this, "Clicked on " + coffee.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent( HomePage.this, Details.class);
        Coffee sendData = coffee;
        intent.putExtra( "COFFEE_OBJECT", sendData );
        startActivity( intent );
    }

    private void setupOnClickListener() {

        ImageButton cartButton = findViewById( R.id.cartButton );
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomePage.this, Cart.class );
                startActivity( intent );
            }
        });

        ImageButton profileButton = findViewById( R.id.profileButton );
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomePage.this, Profile.class );
                startActivity( intent );
            }
        });

        ImageButton historyButton = findViewById( R.id.historyButton_homepage );
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomePage.this, MyOrder.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        ImageButton rewardsButton = findViewById( R.id.redeemButton_homepage );
        rewardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomePage.this, RewardsScreen.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        RelativeLayout card = findViewById( R.id.loyal_card_homepage );
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( loyal_point == 8 ) {
                    Intent intent = new Intent( HomePage.this, RedeemRewardsScreen.class );
                    intent.putExtra("mode", 1 );
                    startActivity(intent);
                }
            }
        });
    }

    private void updateScreen() {

        user = dbHelper.getUser();
        TextView user_name = findViewById( R.id.user_name );
        user_name.setText( user.getName() );

        // Loyal card
        loyal_point = dbHelper.getLoyalPoint();
        loyalAdapter.changOnGoing(loyal_point);
        loyalAdapter.notifyDataSetChanged();
        TextView loyalCount = findViewById( R.id.loyal_count_homepage );
        loyalCount.setText( String.valueOf(loyal_point) + " / 8");
    }

}