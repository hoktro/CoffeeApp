package com.example.coffeeapp;

import java.io.Serializable;
import java.util.List;

public class OrderHistory implements Serializable {

    int orderID;
    private final String[] orderTime;
    private final User orderUser;

    public OrderHistory( int orderID, String[] orderTime, User orderUser ) {
        this.orderID = orderID;
        this.orderTime = orderTime;
        this.orderUser = orderUser;
    }

    public int getOrderID() { return orderID; }
    public String getAddress() { return orderUser.getAddress(); }
    public String getTime() { return orderTime[0] + " | " + orderTime[1].substring(0, Math.min(orderTime[1].length(), 5)); }

}
