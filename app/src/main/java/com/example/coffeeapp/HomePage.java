package com.example.coffeeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements CoffeeAdapter.OnItemClickListener {

    private CoffeeAdapter coffeeAdapter;

    // Get an instance of the CoffeeDBHelper
    private CoffeeDBHelper dbHelper = new CoffeeDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_home_page);

        // setup RecycleView
        dbHelper.initializeCoffeeData();
        dbHelper.initializeUserData();
        setupRecycleView();

        // setup Listener
        setupOnClickListener();
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
    }

}