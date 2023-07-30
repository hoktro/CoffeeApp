package com.example.coffeeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
// import android.widget.Toast;

// import java.text.DecimalFormat;
import com.example.coffeeapp.Class.OrderHistory;
import com.example.coffeeapp.Class.Coffee;
import com.example.coffeeapp.Class.OrderDetails;
import com.example.coffeeapp.Class.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CoffeeDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "coffee_database";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    private static final String TABLE_COFFEE = "coffee_table";
    private static final String TABLE_ORDER = "order_table";
    private static final String TABLE_USER = "user_table";
    private static final String TABLE_ORDER_HISTORY = "order_history";


    // Column set for coffee table
    private static final String KEY_ID = "id";
    private static final String COFFEE_NAME = "name";
    private static final String COFFEE_PRICE = "price";
    private static final String SRC_ID = "image_res_id";

    // Column set for order table

    private static int OrderID = 1;
    private static final String ORDER_ID = "order_id";
    private static final String COFFEE_ID = "coffee_id";
    private static final String AMOUNT = "amount";
    private static final String SHOT = "shot";
    private static final String HOT = "hot";
    private static final String SIZE = "size";
    private static final String ICE = "ice";
    private static final String TOTAL_BILL = "total_bill";

    // Column set for user table
    private static final String USER_NAME = "name";
    private static final String USER_PHONE = "phone";
    private static final String USER_EMAIL = "email";
    private static final String USER_ADDRESS = "address";
    private static final String USER_LOYAL = "loyal";
    private static final String USER_REDEEM = "redeem";
    private static final String USER_ORDER = "ID";

    // Column set for order history table
    private static final String ORDER_TIME = "time";
    private static final String ORDER_NAME = "name";
    private static final String ORDER_PHONE = "phone";
    private static final String ORDER_ADDRESS = "address";
    private static final String ORDER_COMPLETE = "complete";

    // Prepare for creation table query
    private static final String CREATE_TABLE_COFFEE = "CREATE TABLE " + TABLE_COFFEE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COFFEE_NAME + " TEXT NOT NULL, "
            + COFFEE_PRICE + " DOUBLE NOT NULL, "
            + SRC_ID + " INTEGER NOT NULL " +
            ")";

    private static final String CREATE_TABLE_ORDER = "CREATE TABLE " + TABLE_ORDER + "("
            + ORDER_ID + " INTEGER NOT NULL, "
            + COFFEE_ID + " INTEGER NOT NULL, "
            + AMOUNT + " INTEGER NOT NULL, "
            + SHOT + " INTEGER NOT NULL,"
            + HOT + " INTEGER NOT NULL, "
            + SIZE + " INTEGER NOT NULL, "
            + ICE + " INTEGER NOT NULL, "
            + TOTAL_BILL + " DOUBLE NOT NULL" +
            ")";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + USER_NAME + " TEXT, "
            + USER_PHONE + " TEXT, "
            + USER_EMAIL + " TEXT, "
            + USER_ADDRESS + " TEXT,"
            + USER_LOYAL + " INTEGER NOT NULL, "
            + USER_REDEEM + " INTEGER NOT NULL, "
            + USER_ORDER + " INTEGER NOT NULL "
            + ")";

    private static final  String CREATE_TABLE_HISTORY = "CREATE TABLE " + TABLE_ORDER_HISTORY + "("
            + ORDER_ID + " INTEGER NOT NULL, "
            + ORDER_TIME + " DATETIME NOT NULL, "
            + ORDER_NAME + " TEXT, "
            + ORDER_PHONE + " TEXT, "
            + ORDER_ADDRESS + " TEXT,"
            + ORDER_COMPLETE + " INTEGER NOT NULL "
            + ")";


    public CoffeeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( CREATE_TABLE_COFFEE );
        db.execSQL( CREATE_TABLE_ORDER );
        db.execSQL( CREATE_TABLE_USER );
        db.execSQL( CREATE_TABLE_HISTORY );

        // initializeUserData();
        // OrderID = getOrderID();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }

    public void initializeUserData() {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query( TABLE_USER, null, null, null, null, null, null );

        if( cursor == null ) {
            cursor.close();
            db.close();
            return;
        }

        if( cursor.getCount() == 0 ) {
            ContentValues values = new ContentValues();
            values.put( USER_NAME, "Vic Luu" );
            values.put( USER_PHONE, "0777428999" );
            values.put( USER_EMAIL, "Hoktro18@gmail.com" );
            values.put( USER_ADDRESS, "120/19 Tran Binh Trong, P.2, Q.5, HCM" );
            values.put( USER_LOYAL, 7 );
            values.put( USER_REDEEM, 1806 );
            values.put( USER_ORDER, 1 );
            db.insert( TABLE_USER, null, values );
            OrderID = 1;
        }
        else {
            int index = cursor.getColumnIndex( USER_ORDER );
            if( index >= 0 ) while ( cursor.moveToNext() ) OrderID = cursor.getInt( index );
        }

        cursor.close();
        db.close();
    }
    public void initializeCoffeeData() {
        // Sample coffee menu data
        List<Coffee> coffeeList = new ArrayList<>();
        coffeeList.add(new Coffee("Americano", 2.5, R.drawable.americano ));
        coffeeList.add(new Coffee("Cappuccino", 3.5, R.drawable.cappuccino));
        coffeeList.add(new Coffee("Mocha", 4.0, R.drawable.mocha ) );
        coffeeList.add(new Coffee("Flat White", 3.0, R.drawable.flat_white));
        // Add more coffee items as needed

        // Insert the coffee data into the database

        for (Coffee coffee : coffeeList) {
            insertCoffeeData( coffee );
        }

    }
    private void insertCoffeeData( Coffee coffee ) {

        SQLiteDatabase db = getWritableDatabase();

        // Check if the coffee name already exists in the table
        Cursor cursor = db.query(
                CoffeeDBHelper.TABLE_COFFEE,
                null,
                CoffeeDBHelper.COFFEE_NAME + "=?",
                new String[]{coffee.getName()},
                null,
                null,
                null
        );

        if (cursor != null && cursor.getCount() > 0) {
            // The coffee name already exists in the table.
            // You can handle the duplicate name here if needed.
            cursor.close();
            db.close();
            return;
        }

        ContentValues values = new ContentValues();
        values.put( COFFEE_NAME, coffee.getName());
        values.put( COFFEE_PRICE, coffee.getPrice() );
        values.put( SRC_ID, coffee.getImageResourceId());
        db.insert(CoffeeDBHelper.TABLE_COFFEE, null, values);

        if (cursor != null) { cursor.close(); }

        db.close();
    }
    public List<Coffee> getAllCoffee() {
        List<Coffee> coffeeList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                KEY_ID,
                COFFEE_NAME,
                COFFEE_PRICE,
                SRC_ID
        };

        Cursor cursor = db.query( CoffeeDBHelper.TABLE_COFFEE, projection,null,null,null,null,null );

        if( cursor != null ) {
            while (cursor.moveToNext()) {
                // Check if the column index is valid
                // int coffeeIDColumnIndex = cursor.getColumnIndex(KEY_ID);
                int nameColumnIndex = cursor.getColumnIndex(COFFEE_NAME);
                int priceColumnIndex = cursor.getColumnIndex(COFFEE_PRICE);
                int imageResIdColumnIndex = cursor.getColumnIndex(SRC_ID);

                // Check if the column index is valid and greater than or equal to 0
                if( nameColumnIndex >= 0 && priceColumnIndex >= 0 && imageResIdColumnIndex >= 0) {
                    // int CoffeeID = cursor.getInt(coffeeIDColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    double price = cursor.getDouble(priceColumnIndex);
                    int imageResId = cursor.getInt(imageResIdColumnIndex);
                    // Coffee coffee = new Coffee( CoffeeID, name, price, imageResId);
                    Coffee coffee = new Coffee( name, price, imageResId);
                    coffeeList.add(coffee);
                }
            }
        }

        cursor.close();
        db.close();
        return coffeeList;
    }
    public void insertOrderData( OrderDetails details ) {
        SQLiteDatabase db = getWritableDatabase();

        // Get price of coffee
        Coffee coffee = getCoffee( details.getCoffeeID() );
        double price = coffee.getPrice();

        Log.d("Query", "You have go for this !!!!!!!!!!!");

        String selection = ORDER_ID + "=?" + " AND " + COFFEE_ID + "=?"
                + " AND " + SHOT + "=?" + " AND " + HOT + "=?" + " AND " + SIZE + "=?"
                + " AND " + ICE + "=?";
        String[] selectionArgs = { String.valueOf(OrderID), String.valueOf(details.getCoffeeID()), String.valueOf(details.getShot()), String.valueOf(details.getHot()), String.valueOf(details.getSize()), String.valueOf(details.getIce()) };


        Cursor cursor = db.query(
                TABLE_ORDER,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if( cursor == null ) {
            cursor.close();
            db.close();
            return;
        }

        if ( cursor.getCount() == 0 ) {

            Log.d("Query", "Go for null ");

            double totalValue = details.getAmount() * ( price + 0.1 * details.getSize() );
            if( details.getAmount() <= 0 ) totalValue = 0;
            double roundedTotal = Math.round(totalValue * 100.0) / 100.0; // Round to two decimal places

            ContentValues values = new ContentValues();

            values.put( ORDER_ID, OrderID );
            values.put( COFFEE_ID, details.getCoffeeID() );
            values.put( AMOUNT, details.getAmount() );
            values.put( SHOT, details.getShot() );
            values.put( HOT, details.getHot() );
            values.put( SIZE, details.getSize() );
            values.put( ICE, details.getIce() );
            values.put( TOTAL_BILL, roundedTotal );

            db.insert( TABLE_ORDER, null, values);

            cursor.close();
            db.close();
            return;
        }

        // Update the old one if exist

        Log.d("Query", "Go for not null right ?");

        int getTotalIndex = cursor.getColumnIndex( TOTAL_BILL );
        int getAmountIndex = cursor.getColumnIndex( AMOUNT );

        double totalValue = details.getAmount() * ( price + 0.1 * details.getSize() );
        int oldAmount = 0;

        if( getTotalIndex >= 0 ) {
            while ( cursor.moveToNext() ) {
                totalValue += cursor.getDouble(getTotalIndex);
                oldAmount += cursor.getInt(getAmountIndex);
            }
        }
        if( details.getAmount() + oldAmount <= 0 ) totalValue = 0;
        double roundedTotal = Math.round(totalValue * 100.0) / 100.0; // Round to two decimal places

        ContentValues values = new ContentValues();

        values.put( ORDER_ID, OrderID );
        values.put( COFFEE_ID, details.getCoffeeID() );
        values.put( AMOUNT, details.getAmount() + oldAmount );
        values.put( SHOT, details.getShot() );
        values.put( HOT, details.getHot() );
        values.put( SIZE, details.getSize() );
        values.put( ICE, details.getIce() );
        values.put( TOTAL_BILL, roundedTotal );

        int rowsAffected = db.update( TABLE_ORDER, values, selection, selectionArgs);

        // if( rowsAffected != 1 ) ;

        cursor.close();
        db.close();
    }
    public List<OrderDetails> getAllOrder( int orderID ) {

        List<OrderDetails> orderList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String selection = ORDER_ID + "=?";
        String[] selectionArgs = { String.valueOf( orderID ) };

        Cursor cursor = db.query( TABLE_ORDER, null, selection, selectionArgs, null, null, null );

        if( cursor == null ) {
            cursor.close();
            db.close();
            return orderList;
        }

        while( cursor.moveToNext() ) {

            // Check if column index is valid
            int coffeeIDIndex = cursor.getColumnIndex( COFFEE_ID );
            int amountIndex = cursor.getColumnIndex( AMOUNT );
            int shotIndex = cursor.getColumnIndex( SHOT );
            int hotIndex = cursor.getColumnIndex( HOT );
            int sizeIndex = cursor.getColumnIndex( SIZE );
            int iceIndex = cursor.getColumnIndex( ICE );

            if( coffeeIDIndex >= 0 ) {
                int coffeeID = cursor.getInt( coffeeIDIndex );
                int amount = cursor.getInt( amountIndex );
                int shot = cursor.getInt( shotIndex );
                int hot = cursor.getInt( hotIndex );
                int size = cursor.getInt( sizeIndex );
                int ice = cursor.getInt( iceIndex );

                OrderDetails details = new OrderDetails( coffeeID, amount, shot, hot, size, ice );
                orderList.add( details );
            }
        }

        cursor.close();
        db.close();
        return orderList;
    }
    public List<Coffee> getAllCoffee( List<OrderDetails> orderList ) {
        List<Coffee> coffeeList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        for( OrderDetails details : orderList ) {

            Coffee coffee = null;

            String selection = KEY_ID + "=?";
            String[] selectionArgs = { String.valueOf(details.getCoffeeID()) };

            Cursor cursor = db.query( TABLE_COFFEE, null, selection, selectionArgs, null, null, null );

            if( cursor == null ) {
                cursor.close();
                coffeeList.add(coffee);
                continue;
            }
            // Check if the column index is valid
            // int coffeeIDColumnIndex = cursor.getColumnIndex(KEY_ID);
            int nameColumnIndex = cursor.getColumnIndex(COFFEE_NAME);
            int priceColumnIndex = cursor.getColumnIndex(COFFEE_PRICE);
            int imageResIdColumnIndex = cursor.getColumnIndex(SRC_ID);

            // Check if the column index is valid and greater than or equal to 0
            if( nameColumnIndex >= 0 && priceColumnIndex >= 0 && imageResIdColumnIndex >= 0) {
                while( cursor.moveToNext() ) {
                    // int CoffeeID = cursor.getInt(coffeeIDColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    double price = cursor.getDouble(priceColumnIndex);
                    int imageResId = cursor.getInt(imageResIdColumnIndex);
                    // Coffee coffee = new Coffee( CoffeeID, name, price, imageResId);
                    coffee = new Coffee(name, price, imageResId);
                }
            }
            coffeeList.add( coffee );
            cursor.close();
        }

        db.close();
        return coffeeList;
    }
    public Coffee getCoffee( int CoffeeID ) {
        SQLiteDatabase db = getReadableDatabase();
        Coffee coffee = null;

        String selection = KEY_ID + "=?";
        String[] selectionArgs = { String.valueOf(CoffeeID) };

        Cursor cursor = db.query( TABLE_COFFEE, null, selection, selectionArgs, null, null, null );

        if( cursor == null ) {
            cursor.close();
            // db.close();
            return coffee;
        }

        // Check if the column index is valid
        // int coffeeIDColumnIndex = cursor.getColumnIndex(KEY_ID);
        int nameColumnIndex = cursor.getColumnIndex(COFFEE_NAME);
        int priceColumnIndex = cursor.getColumnIndex(COFFEE_PRICE);
        int imageResIdColumnIndex = cursor.getColumnIndex(SRC_ID);

        // Check if the column index is valid and greater than or equal to 0
        if( nameColumnIndex >= 0 && priceColumnIndex >= 0 && imageResIdColumnIndex >= 0) {
            while( cursor.moveToNext() ) {
                // int CoffeeID = cursor.getInt(coffeeIDColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                double price = cursor.getDouble(priceColumnIndex);
                int imageResId = cursor.getInt(imageResIdColumnIndex);
                // Coffee coffee = new Coffee( CoffeeID, name, price, imageResId);
                coffee = new Coffee(name, price, imageResId);
            }
        }

        cursor.close();
        // db.close();
        return coffee;
    }
    public int getCoffeeID( String name ) {
        SQLiteDatabase db = getReadableDatabase();
        int result = 0;

        Cursor cursor = db.query( TABLE_COFFEE, null, COFFEE_NAME + "=?", new String[]{ name }, null, null, null );

        if( cursor == null ) {
            cursor.close();
            db.close();
            return 0;
        }

        int index = cursor.getColumnIndex(KEY_ID);
        if( index >= 0 ) {
            while ( cursor.moveToNext() ) {
                result = cursor.getInt(index);
            }
        }

        cursor.close();
        db.close();
        return result;
    }
    public double getTotalBill( int orderID ) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query( TABLE_ORDER, null, ORDER_ID + "=?", new String[]{ String.valueOf(orderID) }, null, null, null );
        if( cursor == null ) {
            cursor.close();
            db.close();
            return 0.00;
        }

        double result = 0;

        int cashIndex = cursor.getColumnIndex( TOTAL_BILL );
        if( cashIndex >= 0 ) {
            while ( cursor.moveToNext() ) {
                result += cursor.getDouble( cashIndex );
            }
        }
        cursor.close();
        db.close();
        return result;
    }
    public int getOrderID() {
        SQLiteDatabase db = getReadableDatabase();
        int result = 1;

        Cursor cursor = db.query( TABLE_USER, null, null, null, null, null, null );
        if( cursor == null ) {
            cursor.close();
            db.close();
            return result;
        }

        int idIndex = cursor.getColumnIndex( USER_ORDER );
        if( idIndex >= 0 ) while ( cursor.moveToNext() ) result = cursor.getInt(idIndex);

        cursor.close();
        db.close();
        return result;
    }
    public void removeOrder(OrderDetails details) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = ORDER_ID + "=?" + " AND " + COFFEE_ID + "=?"
                + " AND " + SHOT + "=?" + " AND " + HOT + "=?" + " AND " + SIZE + "=?"
                + " AND " + ICE + "=?";
        String[] selectionArgs = { String.valueOf(OrderID), String.valueOf(details.getCoffeeID()), String.valueOf(details.getShot()), String.valueOf(details.getHot()), String.valueOf(details.getSize()), String.valueOf(details.getIce()) };

        db.delete( TABLE_ORDER, selection, selectionArgs );
        db.close();
    }
    public void updateUser( User user ) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query( TABLE_USER, null, null, null, null, null, null );
        if( cursor == null ) {
            cursor.close();
            db.close();
            return;
        }

        int nameIndex = cursor.getColumnIndex( USER_NAME );
        int phoneIndex = cursor.getColumnIndex( USER_PHONE );
        int emailIndex = cursor.getColumnIndex( USER_EMAIL );
        int addressIndex = cursor.getColumnIndex( USER_ADDRESS );
        int loyalIndex = cursor.getColumnIndex( USER_LOYAL );
        int redeemIndex = cursor.getColumnIndex( USER_REDEEM );
        int idIndex = cursor.getColumnIndex( USER_ORDER );

        if( nameIndex >= 0 && cursor.getCount() > 0 ) {
            while (cursor.moveToNext()) {
                ContentValues values = new ContentValues();
                values.put( USER_NAME, user.getName() );
                values.put( USER_PHONE, user.getPhone() );
                values.put( USER_EMAIL, user.getEmail() );
                values.put( USER_ADDRESS, user.getAddress() );
                values.put( USER_LOYAL, cursor.getInt( loyalIndex ) );
                values.put( USER_REDEEM, cursor.getInt( redeemIndex ) );
                values.put( USER_ORDER, cursor.getInt( idIndex ) );
                db.update( TABLE_USER, values, null, null );
            }
        }
        cursor.close();
        db.close();
    }
    public void updateLoyal( int delta ) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query( TABLE_USER, null, null, null, null, null, null );
        if( cursor == null ) {
            cursor.close();
            db.close();
            return;
        }

        int nameIndex = cursor.getColumnIndex( USER_NAME );
        int phoneIndex = cursor.getColumnIndex( USER_PHONE );
        int emailIndex = cursor.getColumnIndex( USER_EMAIL );
        int addressIndex = cursor.getColumnIndex( USER_ADDRESS );
        int loyalIndex = cursor.getColumnIndex( USER_LOYAL );
        int redeemIndex = cursor.getColumnIndex( USER_REDEEM );
        int idIndex = cursor.getColumnIndex( USER_ORDER );

        if( nameIndex >= 0 && cursor.getCount() > 0 ) {
            while (cursor.moveToNext()) {
                ContentValues values = new ContentValues();
                values.put( USER_NAME, cursor.getString( nameIndex ) );
                values.put( USER_PHONE, cursor.getString( phoneIndex ) );
                values.put( USER_EMAIL, cursor.getString( emailIndex ) );
                values.put( USER_ADDRESS, cursor.getString( addressIndex ) );

                int newLoyal = cursor.getInt( loyalIndex ) + delta;
                if( newLoyal > 8 ) newLoyal = 8;
                values.put( USER_LOYAL, newLoyal );

                values.put( USER_REDEEM, cursor.getInt( redeemIndex ) );
                values.put( USER_ORDER, cursor.getInt( idIndex ) );
                db.update( TABLE_USER, values, null, null );
            }
        }
        cursor.close();
        db.close();
    }
    public void updateRedeem( int delta ) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query( TABLE_USER, null, null, null, null, null, null );
        if( cursor == null ) {
            cursor.close();
            db.close();
            return;
        }

        int nameIndex = cursor.getColumnIndex( USER_NAME );
        int phoneIndex = cursor.getColumnIndex( USER_PHONE );
        int emailIndex = cursor.getColumnIndex( USER_EMAIL );
        int addressIndex = cursor.getColumnIndex( USER_ADDRESS );
        int loyalIndex = cursor.getColumnIndex( USER_LOYAL );
        int redeemIndex = cursor.getColumnIndex( USER_REDEEM );
        int idIndex = cursor.getColumnIndex( USER_ORDER );

        if( nameIndex >= 0 && cursor.getCount() > 0 ) {
            while (cursor.moveToNext()) {
                ContentValues values = new ContentValues();
                values.put( USER_NAME, cursor.getString( nameIndex ) );
                values.put( USER_PHONE, cursor.getString( phoneIndex ) );
                values.put( USER_EMAIL, cursor.getString( emailIndex ) );
                values.put( USER_ADDRESS, cursor.getString( addressIndex ) );
                values.put( USER_LOYAL, cursor.getInt( loyalIndex ) );

                int newRedeem = cursor.getInt( redeemIndex ) + delta;
                values.put( USER_REDEEM, newRedeem );

                values.put( USER_ORDER, cursor.getInt( idIndex ) );
                db.update( TABLE_USER, values, null, null );
            }
        }
        cursor.close();
        db.close();
    }
    public void updateID() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query( TABLE_USER, null, null, null, null, null, null );
        if( cursor == null ) {
            cursor.close();
            db.close();
            return;
        }

        int nameIndex = cursor.getColumnIndex( USER_NAME );
        int phoneIndex = cursor.getColumnIndex( USER_PHONE );
        int emailIndex = cursor.getColumnIndex( USER_EMAIL );
        int addressIndex = cursor.getColumnIndex( USER_ADDRESS );
        int loyalIndex = cursor.getColumnIndex( USER_LOYAL );
        int redeemIndex = cursor.getColumnIndex( USER_REDEEM );
        int idIndex = cursor.getColumnIndex( USER_ORDER );

        if( nameIndex >= 0 && cursor.getCount() > 0 ) {
            while (cursor.moveToNext()) {
                ContentValues values = new ContentValues();
                values.put( USER_NAME, cursor.getString( nameIndex ) );
                values.put( USER_PHONE, cursor.getString( phoneIndex ) );
                values.put( USER_EMAIL, cursor.getString( emailIndex ) );
                values.put( USER_ADDRESS, cursor.getString( addressIndex ) );
                values.put( USER_LOYAL, cursor.getInt( loyalIndex ) );
                values.put( USER_REDEEM, cursor.getInt( redeemIndex ) );
                values.put( USER_ORDER, cursor.getInt( idIndex ) + 1 );
                db.update( TABLE_USER, values, null, null );
                OrderID = cursor.getInt( idIndex ) + 1;
            }
        }
        cursor.close();

        // Push order to history
        pushOrder( OrderID );

        db.close();
    }
    public User getUser() {
        SQLiteDatabase db = getReadableDatabase();
        User user = null;

        Cursor cursor = db.query( TABLE_USER, null, null, null, null, null, null);
        if( cursor == null ) {
            cursor.close();
            db.close();
            return user;
        }

        int nameIndex = cursor.getColumnIndex( USER_NAME );
        int phoneIndex = cursor.getColumnIndex( USER_PHONE );
        int emailIndex = cursor.getColumnIndex( USER_EMAIL );
        int addressIndex = cursor.getColumnIndex( USER_ADDRESS );

        if( nameIndex >= 0 && cursor.getCount() > 0 ) {
            cursor.moveToFirst();
            String user_name = cursor.getString( nameIndex );
            String user_phone = cursor.getString( phoneIndex );
            String user_email = cursor.getString( emailIndex );
            String user_address = cursor.getString( addressIndex );
            user = new User( user_name, user_phone, user_email, user_address );
        }
        cursor.close();
        db.close();
        return user;
    }

    private void pushOrder( int ID ) {
        User current_user = getUser();

        SQLiteDatabase db = getWritableDatabase();
        Log.d("pushOrder", "pushData" );

        // Get the current date and time in ISO 8601 format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());

        Log.d("pushOrder", currentDateTime );

        // Now, insert the data into the table with the DATETIME value
        ContentValues values = new ContentValues();
        values.put( ORDER_ID, String.valueOf( ID - 1 ) );
        values.put( ORDER_TIME, currentDateTime );
        values.put( ORDER_NAME, current_user.getName() );
        values.put( ORDER_PHONE, current_user.getPhone() );
        values.put( ORDER_ADDRESS, current_user.getAddress() );
        values.put( ORDER_COMPLETE, String.valueOf(0) );
        db.insert( TABLE_ORDER_HISTORY, null, values );

        db.close();
    }

    public List<OrderHistory> getHistory() {
        SQLiteDatabase db = getReadableDatabase();

//        // Get the time that is 30 minutes before the current time in SQLite datetime format
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.MINUTE, -30);
//        String thirtyMinutesAgo = dateFormat.format(cal.getTime());
//
//        // Build the SQL query to select tuples earlier than thirty minutes ago
//        // String[] columns = new String[]{COLUMN_ID, COLUMN_DATETIME};
//        String selection = ORDER_TIME + " < ?";
//        String[] selectionArgs = new String[]{thirtyMinutesAgo};

        String selection = ORDER_COMPLETE + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(1)};

        Cursor cursor = db.query( TABLE_ORDER_HISTORY, null, selection, selectionArgs, null, null, null);

        List<OrderHistory> result = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                // Prepare orderDetails list
                int orderID = cursor.getInt( cursor.getColumnIndexOrThrow(ORDER_ID) );
                // List<OrderDetails> orderDetails = getAllOrder( orderID );

                // Prepare orderTime

                String[] orderTime = new String[2];
                String datetimeValue = cursor.getString(cursor.getColumnIndexOrThrow(ORDER_TIME));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                try {
                    Date dateTime = sdf.parse(datetimeValue);
                    SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

                    orderTime[0] = dateOnlyFormat.format(dateTime); // Date
                    orderTime[1] = timeOnlyFormat.format(dateTime); // Time

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Prepare current user
                String userName = cursor.getString( cursor.getColumnIndexOrThrow(ORDER_NAME) );
                String userPhone = cursor.getString( cursor.getColumnIndexOrThrow(ORDER_PHONE) );
                String userAddress = cursor.getString( cursor.getColumnIndexOrThrow(ORDER_ADDRESS) );
                User user = new User( userName, userPhone, null, userAddress  );

                // Push to result list
                // result.add( new OrderHistory( orderID, orderDetails, orderTime, user ) );
                result.add( new OrderHistory( orderID, orderTime, user ) );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Collections.reverse(result);

        return result;
    }
    public List<OrderHistory> getOnGoing() {
        SQLiteDatabase db = getReadableDatabase();

        Log.d("Get Ongoing", " ============ On going track start ========== " );

//        // Get the time that is 30 minutes before the current time in SQLite datetime format
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.MINUTE, -30);
//        String thirtyMinutesAgo = dateFormat.format(cal.getTime());
//
//        // Build the SQL query to select tuples earlier than thirty minutes ago
//        String selection = ORDER_TIME + " >= ?";
//        String[] selectionArgs = new String[]{thirtyMinutesAgo};

        String selection = ORDER_COMPLETE + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(0)};

        Cursor cursor = db.query( TABLE_ORDER_HISTORY, null, selection, selectionArgs, null, null, null);
        // Cursor cursor = db.query( TABLE_ORDER_HISTORY, null, null, null, null, null, null);

        Log.d("Get Ongoing", "Tuples: " + String.valueOf(cursor.getCount()) );

        List<OrderHistory> result = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                // Prepare orderDetails list
                int orderID = cursor.getInt( cursor.getColumnIndexOrThrow(ORDER_ID) );
                // List<OrderDetails> orderDetails = getAllOrder( orderID );

                // Prepare orderTime

                String[] orderTime = new String[2];
                String datetimeValue = cursor.getString(cursor.getColumnIndexOrThrow(ORDER_TIME));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                try {
                    Date dateTime = sdf.parse(datetimeValue);
                    SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

                    orderTime[0] = dateOnlyFormat.format(dateTime); // Date
                    orderTime[1] = timeOnlyFormat.format(dateTime); // Time

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("Get Ongoing", orderTime[0] + " | " + orderTime[1] );

                // Prepare current user
                String userName = cursor.getString( cursor.getColumnIndexOrThrow(ORDER_NAME) );
                String userPhone = cursor.getString( cursor.getColumnIndexOrThrow(ORDER_PHONE) );
                String userAddress = cursor.getString( cursor.getColumnIndexOrThrow(ORDER_ADDRESS) );
                User user = new User( userName, userPhone, null, userAddress  );

                // Push to result list
                // result.add( new OrderHistory( orderID, orderDetails, orderTime, user ) );
                result.add( new OrderHistory( orderID, orderTime, user ) );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Collections.reverse(result);

        return result;
    }
    public List<OrderHistory> getRewardsHistory() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query( TABLE_ORDER_HISTORY, null, null, null, null, null, null);
        // Cursor cursor = db.query( TABLE_ORDER_HISTORY, null, null, null, null, null, null);

        Log.d("Get Ongoing", "Tuples: " + String.valueOf(cursor.getCount()) );

        List<OrderHistory> result = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                // Prepare orderDetails list
                int orderID = cursor.getInt( cursor.getColumnIndexOrThrow(ORDER_ID) );
                // List<OrderDetails> orderDetails = getAllOrder( orderID );

                // Prepare orderTime

                String[] orderTime = new String[2];
                String datetimeValue = cursor.getString(cursor.getColumnIndexOrThrow(ORDER_TIME));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                try {
                    Date dateTime = sdf.parse(datetimeValue);
                    SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

                    orderTime[0] = dateOnlyFormat.format(dateTime); // Date
                    orderTime[1] = timeOnlyFormat.format(dateTime); // Time

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("Get Ongoing", orderTime[0] + " | " + orderTime[1] );

                // Prepare current user
                String userName = cursor.getString( cursor.getColumnIndexOrThrow(ORDER_NAME) );
                String userPhone = cursor.getString( cursor.getColumnIndexOrThrow(ORDER_PHONE) );
                String userAddress = cursor.getString( cursor.getColumnIndexOrThrow(ORDER_ADDRESS) );
                User user = new User( userName, userPhone, null, userAddress  );

                // Push to result list
                // result.add( new OrderHistory( orderID, orderDetails, orderTime, user ) );
                result.add( new OrderHistory( orderID, orderTime, user ) );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Collections.reverse(result);

        return result;
    }

    public void clearCacheHistory() {
        SQLiteDatabase db = getWritableDatabase();


        // Get the time that is 30 minutes before the current time in SQLite datetime format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -15);
        String fifteenDayAgo = dateFormat.format(cal.getTime());

        // Build the SQL query to select tuples earlier than thirty minutes ago
        String selection = ORDER_TIME + " < ?";
        String[] selectionArgs = new String[]{fifteenDayAgo};

        // Step 1: Create the subquery to get ORDER_IDs from TABLE_ORDER based on the given condition
        String sub_query = "SELECT order_id FROM order_history WHERE " + selection;

        // Step 2: Execute the DELETE query with the subquery as a condition
        String deleteSelection = "order_id IN (" + sub_query + ")";

        // Step 3: Perform the delete operation on the database
        int rowsDeleted = db.delete(TABLE_ORDER, deleteSelection, selectionArgs);

        db.delete( TABLE_ORDER_HISTORY, selection, selectionArgs );

        db.close();
    }

    public String getDescription( int ID ) {
        String result = "";
        List<OrderDetails> orderDetailsList = getAllOrder( ID );

        for( OrderDetails details : orderDetailsList ) {
            Coffee coffee = getCoffee( details.getCoffeeID() );
            result += coffee.getName() + " x " + String.valueOf( Math.abs(details.getAmount()) );
            result += ", ";
        }

        return result;
    }

    public void removeHistory( int orderID ) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = ORDER_ID + " = ?";
        String[] selectionArgs = new String[]{ String.valueOf(orderID) };

        ContentValues values = new ContentValues();
        values.put( ORDER_COMPLETE, 1 );
        db.update( TABLE_ORDER_HISTORY, values, selection, selectionArgs );

        db.close();
    }

    public int getLoyalPoint() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query( TABLE_USER, null, null, null, null, null, null );
        int index = cursor.getColumnIndexOrThrow( USER_LOYAL );
        cursor.moveToFirst();
        int result = cursor.getInt(index);
        Log.d("Loyal", String.valueOf(result) );
        cursor.close();
        db.close();
        return result;
    }

    public int getRedeemPoint() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query( TABLE_USER, null, null, null, null, null, null );
        int index = cursor.getColumnIndexOrThrow( USER_REDEEM );
        cursor.moveToFirst();
        int result = cursor.getInt(index);
        Log.d("Redeem", String.valueOf(result) );
        cursor.close();
        db.close();
        return result;
    }

}
