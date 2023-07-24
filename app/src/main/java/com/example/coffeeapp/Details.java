package com.example.coffeeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Details extends AppCompatActivity {

    private int number_of_cup = 1;
    private int shot = 0; // 0 single 1 double
    private int hot = 1; // 0 hot 1 cold
    private int size = 1; // 0 small 1 medium 2 large
    private int ice = 3; // 0 null 1 once 2 double 3 triple
    private int ice_pre = 0;
    private double price = 0;
    private int coffeeID = 1;
    private CoffeeDBHelper dbHelper = new CoffeeDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_details);

        addEventListener();

        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Retrieve the Coffee object from the Intent's extras
        Coffee coffee = (Coffee) intent.getSerializableExtra("COFFEE_OBJECT");

        TextView coffeeName = findViewById( R.id.details_name );
        coffeeName.setText( coffee.getName() );

        coffeeID = dbHelper.getCoffeeID( coffee.getName() );
        Toast.makeText(this, "Clicked on " + String.valueOf(coffeeID), Toast.LENGTH_SHORT).show();
        price = coffee.getPrice();

        ImageView coffeeImage = findViewById( R.id.details_image );
        coffeeImage.setImageResource( coffee.getImageResourceId() );

        TextView totalShow = findViewById( R.id.total_amount );
        totalShow.setText( "$" + String.valueOf( price + 0.1 * size ));
    }
    private void addEventListener() {

        // Setting for back button
        ImageButton backButton = findViewById( R.id.backButton01);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Setting for number of cups
        TextView decreaseButton = findViewById( R.id.decrementButton );
        TextView increaseButton = findViewById( R.id.incrementButton );
        TextView numberShow = findViewById( R.id.numberTextView );
        TextView totalShow = findViewById( R.id.total_amount );

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_of_cup = number_of_cup + 1;
                // float total = number_of_cup * ( price + (float)0.1 * size );
                numberShow.setText( String.valueOf(number_of_cup) );

                double totalValue = number_of_cup * (price + 0.1 * size);
                double roundedTotal = Math.round(totalValue * 100.0) / 100.0; // Round to two decimal places

                DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format for two decimal places
                String formattedTotal = decimalFormat.format(roundedTotal);

                totalShow.setText("$" + formattedTotal);
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_of_cup = number_of_cup - 1;
                if( number_of_cup < 1 ) number_of_cup = 1;
                // float total = number_of_cup * ( price + (float)0.1 * size );
                numberShow.setText( String.valueOf(number_of_cup) );

                double totalValue = number_of_cup * (price + 0.1 * size);
                double roundedTotal = Math.round(totalValue * 100.0) / 100.0; // Round to two decimal places

                DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format for two decimal places
                String formattedTotal = decimalFormat.format(roundedTotal);

                totalShow.setText("$" + formattedTotal);
            }
        });

        // Get the black color from resources
        int colorBlack = ContextCompat.getColor(this, R.color.black);

        // Create a ColorStateList with the black color
        ColorStateList colorStateList = ColorStateList.valueOf(colorBlack);

        // Setting for choosing shot
        TextView singleSelect = findViewById( R.id.select_single );
        TextView doubleSelect = findViewById( R.id.select_double );

        singleSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shot = 0;
                doubleSelect.setBackgroundTintList(null);
                singleSelect.setBackgroundTintList(colorStateList);
            }
        });

        doubleSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shot = 1;
                singleSelect.setBackgroundTintList(null);
                doubleSelect.setBackgroundTintList(colorStateList);
            }
        });

        // Setting for choosing size
        TextView smallSelect = findViewById( R.id.smallButton );
        TextView mediumSelect = findViewById( R.id.mediumButton );
        TextView largeSelect = findViewById( R.id.largeButton );

        smallSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                size = 0;

                smallSelect.setBackgroundTintList(colorStateList);
                mediumSelect.setBackgroundTintList(null);
                largeSelect.setBackgroundTintList(null);

                double totalValue = number_of_cup * (price + 0.1 * size);
                double roundedTotal = Math.round(totalValue * 100.0) / 100.0; // Round to two decimal places

                DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format for two decimal places
                String formattedTotal = decimalFormat.format(roundedTotal);

                totalShow.setText("$" + formattedTotal);

            }
        });

        mediumSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                size = 1;

                smallSelect.setBackgroundTintList(null);
                mediumSelect.setBackgroundTintList(colorStateList);
                largeSelect.setBackgroundTintList(null);

                double totalValue = number_of_cup * (price + 0.1 * size);
                double roundedTotal = Math.round(totalValue * 100.0) / 100.0; // Round to two decimal places

                DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format for two decimal places
                String formattedTotal = decimalFormat.format(roundedTotal);

                totalShow.setText("$" + formattedTotal);

            }
        });

        largeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                size = 2;

                smallSelect.setBackgroundTintList(null);
                mediumSelect.setBackgroundTintList(null);
                largeSelect.setBackgroundTintList(colorStateList);

                double totalValue = number_of_cup * (price + 0.1 * size);
                double roundedTotal = Math.round(totalValue * 100.0) / 100.0; // Round to two decimal places

                DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format for two decimal places
                String formattedTotal = decimalFormat.format(roundedTotal);

                totalShow.setText("$" + formattedTotal);

            }
        });

        // Setting for choosing ice
        TextView oneIceSelect = findViewById( R.id.iceButton );
        TextView doubleIceSelect = findViewById( R.id.double_iceButton );
        TextView tripleIceSelect = findViewById( R.id.triple_iceButton );

        oneIceSelect.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ice = 1;

                oneIceSelect.setBackgroundTintList(colorStateList);
                doubleIceSelect.setBackgroundTintList(null);
                tripleIceSelect.setBackgroundTintList(null);

            }
        } );

        doubleIceSelect.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ice = 2;

                oneIceSelect.setBackgroundTintList(null);
                doubleIceSelect.setBackgroundTintList(colorStateList);
                tripleIceSelect.setBackgroundTintList(null);

            }
        } );

        tripleIceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ice = 3;

                oneIceSelect.setBackgroundTintList(null);
                doubleIceSelect.setBackgroundTintList(null);
                tripleIceSelect.setBackgroundTintList(colorStateList);

            }
        });

        // Setting for choosing hot or cold
        TextView hotSelect = findViewById( R.id.hotButton );
        TextView coldSelect = findViewById( R.id.coldButton );

        hotSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hot = 0;
                hotSelect.setBackgroundTintList(colorStateList);
                coldSelect.setBackgroundTintList(null);

                //
                RelativeLayout iceView = findViewById( R.id.select_ice );
                iceView.setVisibility( View.GONE );

                // Change ice value to 0
                ice_pre = ice;
                ice = 0;

//                // Disabled ice choosing
//                oneIceSelect.setOnClickListener(null);
//                doubleIceSelect.setOnClickListener(null);
//                tripleIceSelect.setOnClickListener(null);
//
//                // Disabled color of ice
//                oneIceSelect.setBackgroundTintList(null);
//                doubleIceSelect.setBackgroundTintList(null);
//                tripleIceSelect.setBackgroundTintList(null);
            }
        });

        coldSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hot = 1;
                hotSelect.setBackgroundTintList(null);
                coldSelect.setBackgroundTintList(colorStateList);

                //
                RelativeLayout iceView = findViewById( R.id.select_ice );
                iceView.setVisibility( View.VISIBLE );

                // Change ice value to 3
                ice = ice_pre;

            }
        });

        // Setting for add button
        Button addButton = findViewById( R.id.addButton );

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetails newOrder = new OrderDetails( coffeeID, number_of_cup, shot, hot, size, ice );
                dbHelper.insertOrderData( newOrder );
                finish();
            }
        });

    }

}