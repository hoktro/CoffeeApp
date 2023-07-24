package com.example.coffeeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
// import android.widget.Toast;

// import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CoffeeDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "coffee_database";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    private static final String TABLE_COFFEE = "coffee_table";
    private static final String TABLE_ORDER = "order_table";
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

    // Column set for order history table



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


    public CoffeeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( CREATE_TABLE_COFFEE );
        db.execSQL( CREATE_TABLE_ORDER );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
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

    public void increaseOrderID() {
        OrderID = OrderID + 1;
    }

    public void insertOrderData( OrderDetails details ) {
        SQLiteDatabase db = getWritableDatabase();

        // Get price of coffee
        Coffee coffee = getCoffee( details.getCoffeeID() );
        double price = coffee.getPrice();

        Log.d("Query", "You have go for this !!!!!!!!!!!");

//        double totalValue = details.getAmount() * ( price + 0.1 * details.getSize() );
//        double roundedTotal = Math.round(totalValue * 100.0) / 100.0; // Round to two decimal places
//
//        ContentValues values = new ContentValues();
//
//        values.put( ORDER_ID, OrderID );
//        values.put( COFFEE_ID, details.getCoffeeID() );
//        values.put( AMOUNT, details.getAmount() );
//        values.put( SHOT, details.getShot() );
//        values.put( HOT, details.getHot() );
//        values.put( SIZE, details.getSize() );
//        values.put( ICE, details.getIce() );
//        values.put( TOTAL_BILL, roundedTotal );
//
//        db.insert( TABLE_ORDER, null, values);
//        db.close();

        // Check if the coffee order exists in the table

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
    public int getOrderID() { return OrderID; }

    public void removeOrder(OrderDetails details) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = ORDER_ID + "=?" + " AND " + COFFEE_ID + "=?"
                + " AND " + SHOT + "=?" + " AND " + HOT + "=?" + " AND " + SIZE + "=?"
                + " AND " + ICE + "=?";
        String[] selectionArgs = { String.valueOf(OrderID), String.valueOf(details.getCoffeeID()), String.valueOf(details.getShot()), String.valueOf(details.getHot()), String.valueOf(details.getSize()), String.valueOf(details.getIce()) };

        db.delete( TABLE_ORDER, selection, selectionArgs );
        db.close();
    }
}
