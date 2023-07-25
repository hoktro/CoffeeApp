package com.example.coffeeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_profile);

        setListener();
    }

    private void setListener() {

        Log.d("Profile", "setListener" );

        ConstraintLayout parentLayout = findViewById(R.id.parentLayout);

        // Edit name
        ImageView editName = findViewById( R.id.editName );
        EditText profileName = findViewById( R.id.profileName );

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Profile", "Click button editName" );
                profileName.setEnabled( true );
                profileName.requestFocus();
            }
        });

        // Edit phone
        ImageView editPhone = findViewById( R.id.editPhone );
        EditText profilePhone = findViewById( R.id.profilePhone );

        editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePhone.setEnabled( true );
                profilePhone.requestFocus();
            }
        });

        // Edit email
        ImageView editEmail = findViewById( R.id.editEmail );
        EditText profileEmail = findViewById( R.id.profileEmail );

        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileEmail.setEnabled( true );
                profileEmail.requestFocus();
            }
        });

        // Edit name
        ImageView editAddress = findViewById( R.id.editAddress );
        EditText profileAddress = findViewById( R.id.profileAddress );

        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileAddress.setEnabled( true );
                profileAddress.requestFocus();
            }
        });

        // Set for layout
        parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Check if the EditText has focus and the touch event is outside the EditText
                if (profileName.hasFocus() && !isPointInsideView(event.getRawX(), event.getRawY(), profileName)) {
                    profileName.setEnabled(false); // Disable editing
                }
                // Check if the EditText has focus and the touch event is outside the EditText
                if (profilePhone.hasFocus() && !isPointInsideView(event.getRawX(), event.getRawY(), profilePhone)) {
                    profilePhone.setEnabled(false); // Disable editing
                }
                // Check if the EditText has focus and the touch event is outside the EditText
                if (profileEmail.hasFocus() && !isPointInsideView(event.getRawX(), event.getRawY(), profileEmail)) {
                    profileEmail.setEnabled(false); // Disable editing
                }
                // Check if the EditText has focus and the touch event is outside the EditText
                if (profileAddress.hasFocus() && !isPointInsideView(event.getRawX(), event.getRawY(), profileAddress)) {
                    profileAddress.setEnabled(false); // Disable editing
                }
                return false;
            }
        });
    }

    // Helper method to check if the touch event is inside the specified view
    private boolean isPointInsideView(float x, float y, View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        return (x > viewX && x < (viewX + viewWidth) && y > viewY && y < (viewY + viewHeight));
    }
}