package com.example.coffeeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.coffeeapp.CoffeeDBHelper;
import com.example.coffeeapp.R;

public class SplashScreen extends AppCompatActivity {

    CoffeeDBHelper dbHelper = new CoffeeDBHelper( this );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash_screen);

        dbHelper.initializeUserData();
        dbHelper.initializeCoffeeData();
        dbHelper.clearCacheHistory();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( SplashScreen.this, HomePage.class );
                startActivity( intent );
                finish();
            }
        }, 2000 );
    }
}