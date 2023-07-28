package com.example.coffeeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.coffeeapp.Class.OrderDetails;
import com.example.coffeeapp.CoffeeDBHelper;
import com.example.coffeeapp.R;

public class RedeemRewardsScreen extends AppCompatActivity {

    private boolean allowClick_mode0 = true;
    private boolean allowClick_mode1 = false;
    private TextView button01;
    private TextView button02;
    private TextView button03;
    private int payment = 1340;
    private int current;
    private CoffeeDBHelper dbHelper = new CoffeeDBHelper(this);

    private int Mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_rewards_screen);

        Intent intent = getIntent();
        Mode = intent.getIntExtra("mode", 0 );

        // Find button
        button01 = findViewById( R.id.RedeemButton01 );
        button02 = findViewById( R.id.RedeemButton02 );
        button03 = findViewById( R.id.RedeemButton03 );

        setupListener();

        updateScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateScreen();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Mode = 0;
        updateScreen();
    }

    private void setupListener() {

        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For mode 0
                if( allowClick_mode0 ) {
                    dbHelper.updateRedeem( -payment );
                    OrderDetails details = new OrderDetails( 1, -1, -1, -1, -1, -1 );
                    dbHelper.insertOrderData( details );
                    updateScreen();
                }

                // For mode 1
                if( allowClick_mode1) {
                    dbHelper.updateLoyal( -8 );
                    OrderDetails details = new OrderDetails( 1, -1, -1, -1, -1, -1 );
                    dbHelper.insertOrderData( details );
                    updateScreen();
                }
            }
        });

        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For mode 0
                if( allowClick_mode0 ) {
                    dbHelper.updateRedeem( -payment );
                    OrderDetails details = new OrderDetails( 4, -1, -1, -1, -1, -1 );
                    dbHelper.insertOrderData( details );
                    updateScreen();
                }

                // For mode 1
                if( allowClick_mode1) {
                    dbHelper.updateLoyal( -8 );
                    OrderDetails details = new OrderDetails( 4, -1, -1, -1, -1, -1 );
                    dbHelper.insertOrderData( details );
                    updateScreen();
                }
            }
        });

        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For mode 0
                if( allowClick_mode0 ) {
                    dbHelper.updateRedeem( -payment );
                    OrderDetails details = new OrderDetails( 2, -1, -1, -1, -1, -1 );
                    dbHelper.insertOrderData( details );
                    updateScreen();
                }

                // For mode 1
                if( allowClick_mode1) {
                    dbHelper.updateLoyal( -8 );
                    OrderDetails details = new OrderDetails( 2, -1, -1, -1, -1, -1 );
                    dbHelper.insertOrderData( details );
                    updateScreen();
                }
            }
        });

        // Setting for back button
        ImageButton backButton = findViewById( R.id.backButton03);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    int backgroundColor = Color.parseColor("#80324A59");

    // Create a ColorStateList with the desired color
    ColorStateList colorStateList = ColorStateList.valueOf(backgroundColor);


    private void updateScreen() {
        current = dbHelper.getRedeemPoint();

        // Mode redeem exchange
        if( Mode == 0 ) {

            allowClick_mode1 = false;
            button01.setText("1340 pts");
            button02.setText("1340 pts");
            button03.setText("1340 pts");

            if( current >= payment ) {

                button01.setBackgroundTintList(null);
                button02.setBackgroundTintList(null);
                button03.setBackgroundTintList(null);

                allowClick_mode0 = true;

                return;
            }

            button01.setBackgroundTintList(colorStateList);
            button02.setBackgroundTintList(colorStateList);
            button03.setBackgroundTintList(colorStateList);

            allowClick_mode0 = false;
        }

        // Mode loyal exchange
        if( Mode == 1 ) {

            allowClick_mode0 = false;
            button01.setText("Exchange");
            button02.setText("Exchange");
            button03.setText("Exchange");

            // Allow all
            if( dbHelper.getLoyalPoint() == 8 ) {

                button01.setBackgroundTintList(null);
                button02.setBackgroundTintList(null);
                button03.setBackgroundTintList(null);

                allowClick_mode1 = true;

            }
            else {

                button01.setBackgroundTintList(colorStateList);
                button02.setBackgroundTintList(colorStateList);
                button03.setBackgroundTintList(colorStateList);

                allowClick_mode1 = false;

            }


        }

    }

}