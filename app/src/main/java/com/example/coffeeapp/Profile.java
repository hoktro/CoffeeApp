package com.example.coffeeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private User user;
    private final CoffeeDBHelper dbHelper = new CoffeeDBHelper( this );

    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText emailEdit;
    private EditText addressEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_profile);

        nameEdit = findViewById(R.id.profileName);
        phoneEdit = findViewById(R.id.profilePhone);
        emailEdit = findViewById(R.id.profileEmail);
        addressEdit = findViewById(R.id.profileAddress);

        updateScreen();

        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateScreen();
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateInfo();
    }

    private void setListener() {

        // Test
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                if (newText.isEmpty()) {
                    nameEdit.setText(" ");
                    nameEdit.setSelection(1);
                } else if((newText.length() == 2) && (newText.charAt(0) == ' ')) {
                    newText = newText.substring(1);
                    nameEdit.setText(newText);
                }
            }
        });
        phoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                if (newText.isEmpty()) {
                    phoneEdit.setText(" ");
                    phoneEdit.setSelection(1);
                } else if((newText.length() == 2) && (newText.charAt(0) == ' ')) {
                    newText = newText.substring(1);
                    phoneEdit.setText(newText);
                }
            }
        });
        emailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                if (newText.isEmpty()) {
                    emailEdit.setText(" ");
                    emailEdit.setSelection(1);
                } else if((newText.length() == 2) && (newText.charAt(0) == ' ')) {
                    newText = newText.substring(1);
                    emailEdit.setText(newText);
                }
            }
        });
        addressEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                if (newText.isEmpty()) {
                    addressEdit.setText(" ");
                    addressEdit.setSelection(1);
                } else if((newText.length() == 2) && (newText.charAt(0) == ' ')) {
                    newText = newText.substring(1);
                    addressEdit.setText(newText);
                }
            }
        });

        //

        Log.d("Profile", "setListener" );

        ConstraintLayout parentLayout = findViewById(R.id.parentLayout);

        // Back arrow
        ImageButton backButton = findViewById( R.id.profile_backButton );
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Edit name
        ImageView editName = findViewById( R.id.editName );

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Profile", "Click button editName" );
                nameEdit.setEnabled( true );
                nameEdit.requestFocus();
            }
        });

        // Edit phone
        ImageView editPhone = findViewById( R.id.editPhone );

        editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneEdit.setEnabled( true );
                phoneEdit.requestFocus();
            }
        });

        // Edit email
        ImageView editEmail = findViewById( R.id.editEmail );

        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailEdit.setEnabled( true );
                emailEdit.requestFocus();
            }
        });

        // Edit name
        ImageView editAddress = findViewById( R.id.editAddress );

        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressEdit.setEnabled( true );
                addressEdit.requestFocus();
            }
        });

        // Set for layout
        parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Check if the EditText has focus and the touch event is outside the EditText
                if (nameEdit.hasFocus() && !isPointInsideView(event.getRawX(), event.getRawY(), nameEdit)) {
                    nameEdit.setEnabled(false); // Disable editing
                    user.changeName(String.valueOf(nameEdit.getText()));
                    Log.d("Profile", "name change: " + user.getName() );
                }
                // Check if the EditText has focus and the touch event is outside the EditText
                if (phoneEdit.hasFocus() && !isPointInsideView(event.getRawX(), event.getRawY(), phoneEdit)) {
                    phoneEdit.setEnabled(false); // Disable editing
                    user.changePhone(String.valueOf(phoneEdit.getText()));
                }
                // Check if the EditText has focus and the touch event is outside the EditText
                if (emailEdit.hasFocus() && !isPointInsideView(event.getRawX(), event.getRawY(), emailEdit)) {
                    emailEdit.setEnabled(false); // Disable editing
                    user.changeEmail(String.valueOf(emailEdit.getText()));
                }
                // Check if the EditText has focus and the touch event is outside the EditText
                if (addressEdit.hasFocus() && !isPointInsideView(event.getRawX(), event.getRawY(), addressEdit)) {
                    addressEdit.setEnabled(false); // Disable editing
                    user.changeAddress(String.valueOf(addressEdit.getText()));
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

    private void updateScreen() {
        // Get current user data
        user = dbHelper.getUser();
        nameEdit.setText( user.getName() );
        phoneEdit.setText( user.getPhone() );
        emailEdit.setText( user.getEmail() );
        addressEdit.setText( user.getAddress() );
    }

    private void updateInfo() {
        Log.d("Profile", "name update: " + user.getName() );
        dbHelper.updateUser( user );
    }
}