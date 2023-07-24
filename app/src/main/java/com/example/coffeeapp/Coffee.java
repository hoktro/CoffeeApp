package com.example.coffeeapp;

// Coffee.java

import java.io.Serializable;

public class Coffee implements Serializable {
    private String name;

    private  double price;
    private int imageResourceId; // New field to hold the coffee image resource ID

    public Coffee( String name, double price, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
