package com.example.coffeeapp;

import java.io.Serializable;

public class OrderDetails implements Serializable {

    private int CoffeeID;
    private int amount;
    private int shot;
    private int hot;
    private int size;
    private int ice;


    public OrderDetails( int CoffeeID, int amount, int shot, int hot, int size, int ice ) {
        this.CoffeeID = CoffeeID;
        this.amount = amount;
        this.shot = shot;
        this.hot = hot;
        this.size = size;
        this.ice = ice;
    }

    public int getCoffeeID() { return CoffeeID; }
    public int getAmount() { return amount; }
    public int getShot() { return shot; }
    public int getHot() { return hot; }
    public int getSize() { return size; }
    public int getIce() { return ice; }

}
